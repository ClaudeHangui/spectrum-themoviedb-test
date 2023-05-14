package com.example.spectrum_themoviedb_test.util

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import android.os.Build
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class InternetStateManager @Inject constructor(private val connectivityManager: ConnectivityManager,
                                               private val networkRequestBuilder: NetworkRequest.Builder,
                                               private val sdkManager: SdkManager){

    fun observeNetworkChangeState(): Flow<Boolean> = callbackFlow {
        val connectivityManagerCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                trySend(true)
            }

            override fun onUnavailable() {
                super.onUnavailable()
                trySend(false)
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                trySend(false)
            }
        }

        when {
            sdkManager.isSdkVersionAtLeast(Build.VERSION_CODES.N) ->
                connectivityManager.registerDefaultNetworkCallback(connectivityManagerCallback)

            else -> connectivityManager.registerNetworkCallback(
                networkRequestBuilder.build(),
                connectivityManagerCallback
            )
        }
        awaitClose {
            connectivityManager.unregisterNetworkCallback(connectivityManagerCallback)
        }
    }.distinctUntilChanged().flowOn(Dispatchers.IO)
}