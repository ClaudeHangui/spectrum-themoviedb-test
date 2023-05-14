package com.example.spectrum_themoviedb_test.util

import javax.inject.Inject

class SdkManager @Inject constructor() {
    fun getSdkVersion(): String {
        return android.os.Build.VERSION.SDK_INT.toString()
    }

    fun getSdkVersionInt(): Int {
        return android.os.Build.VERSION.SDK_INT
    }

    fun isSdkVersionAtLeast(version: Int): Boolean {
        return android.os.Build.VERSION.SDK_INT >= version
    }
}