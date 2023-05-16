package com.example.spectrum_themoviedb_test.util

import javax.inject.Inject

class DateFormatterHelper @Inject constructor(private val dateWrapper: DateWrapper) {
    fun formatDate(date: String, datePattern: String): String {
        val formatter = dateWrapper.getFormatterForPattern(datePattern)
        val parsedLocalDate = dateWrapper.getParsedLocalDate(date)
        return parsedLocalDate.format(formatter)
    }
}