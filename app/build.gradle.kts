plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.kapt")
    id("org.jetbrains.kotlin.android")
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
}

dependencies {
    implementation(libs.bundles.androidx.core.deps)
    implementation(libs.bundles.jetpack.compose.deps)
    testImplementation(libs.bundles.local.test.deps)
    androidTestImplementation(libs.bundles.android.test.deps)
    debugImplementation(libs.bundles.debug.api.deps)
}
