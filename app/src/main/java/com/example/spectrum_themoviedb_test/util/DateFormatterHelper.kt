package com.example.spectrum_themoviedb_test.util

import javax.inject.Inject

class DateFormatterHelper @Inject constructor(private val dateWrapper: DateWrapper) {
    fun formatDate(date: String): String {
        val formatter = dateWrapper.getFormatterForPattern("dd/mmm/yyyy")
        val parsedLocalDate = dateWrapper.getParsedLocalDate(date)
        return parsedLocalDate.format(formatter)
    }
}