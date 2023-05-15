package com.example.spectrum_themoviedb_test.util

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject

class DateWrapper @Inject constructor() {
    fun getFormatterForPattern(datePattern: String): DateTimeFormatter = DateTimeFormatter.ofPattern(datePattern, Locale.ENGLISH)
    fun getParsedLocalDate(dateToParse: String): LocalDate = LocalDate.parse(dateToParse)
}