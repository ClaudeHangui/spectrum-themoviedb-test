package com.example.spectrum_themoviedb_test

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import com.example.spectrum_themoviedb_test.data.MovieDbApi
import com.example.spectrum_themoviedb_test.util.Constants.BASE_URL
import com.example.spectrum_themoviedb_test.util.InternetStateManager
import com.example.spectrum_themoviedb_test.util.SdkManager
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
abstract class CoreModule {
    companion object {
        private const val TIMEOUT = 60L

        @Provides
        fun provideConnectivityManager(@ApplicationContext context: Context): ConnectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        @Provides
        fun provideNetworkRequestBuilder(): NetworkRequest.Builder = NetworkRequest.Builder()
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)

        @Provides
        fun provideLoggingInterceptor(): HttpLoggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

        @Provides
        fun provideApiInterceptor(): Interceptor = Interceptor { chain: Interceptor.Chain ->
            var request = chain.request()
            val url = request.url.newBuilder().build()
            request = request.newBuilder().url(url).build()
            chain.proceed(request)
        }

        @Provides
        fun provideGsonConverter(): Gson {
            return GsonBuilder().create()
        }

        @Provides
        fun provideRetrofitBuilder(
            okHttpClient: OkHttpClient,
            gson: Gson
        ): Retrofit.Builder {
            return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
        }

        @Provides
        fun provideOkHttpClient(
            apiInterceptor: Interceptor,
            loggingInterceptor: HttpLoggingInterceptor
        ): OkHttpClient = OkHttpClient.Builder()
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(apiInterceptor)
            .addInterceptor(loggingInterceptor)
            .retryOnConnectionFailure(true)
            .build()

        @Provides
        fun provideSdkManager() = SdkManager()

        @Provides
        fun provideInternetStateManager(
            connectivityManager: ConnectivityManager,
            networkRequest: NetworkRequest.Builder,
            sdkManager: SdkManager
        ) =
            InternetStateManager(connectivityManager, networkRequest, sdkManager)

        @Provides
        fun provideRestService(retrofitBuilder: Retrofit.Builder): MovieDbApi {
            return retrofitBuilder.baseUrl(BASE_URL)
                .build()
                .create(MovieDbApi::class.java)
        }
    }
}