package com.example.spectrum_themoviedb_test.data.local

import androidx.room.TypeConverter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


class IntListTypeConverter {
    @TypeConverter
    fun fromList(value: List<Int>) = Json.encodeToString(value)

    @TypeConverter
    fun toList(value : Int) = Json.decodeFromString<List<Int>>(value.toString())
}
