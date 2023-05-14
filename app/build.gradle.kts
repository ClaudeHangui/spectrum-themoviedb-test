@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android.application)
    kotlin("android")
    kotlin("kapt")
    alias(libs.plugins.hilt.android)
}

android {
    namespace = "com.example.spectrum_themoviedb_test"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.example.spectrum_themoviedb_test"
        minSdk = 21
        targetSdk = 33
        versionCode = 20220822
        versionName = "0.0.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles (
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get()
    }

    kotlin {
        jvmToolchain {
            languageVersion.set(JavaLanguageVersion.of(11))
        }
    }

    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1,gradle-plugins}"
        }
    }
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
            isReturnDefaultValues = true
        }
    }
}

dependencies {
    implementation(libs.bundles.androidx.core.deps)
    implementation(platform(libs.compose.bom))
    implementation(libs.bundles.jetpack.compose.deps)
    implementation(libs.bundles.di.deps)
    implementation(libs.room.ktx)
    implementation(libs.bundles.network.deps)
    implementation(libs.bundles.coroutines.deps)
    kapt(libs.hilt.compiler)
    kapt(libs.room.compiler)
    testImplementation(libs.bundles.local.test.deps)
    testRuntimeOnly(libs.junit.jupiter.engine)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.bundles.android.test.deps)
    debugImplementation(libs.bundles.debug.compose.deps)
}

tasks.withType<Test> {
    useJUnitPlatform()
}
