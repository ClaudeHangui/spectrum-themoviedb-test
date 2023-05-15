package com.example.spectrum_themoviedb_test.util

import android.content.Context
import com.example.spectrum_themoviedb_test.R
import javax.inject.Inject

class ResourceHelper @Inject constructor(appContext: Context) {
    val apiKey = appContext.getString(R.string.api_key)
}