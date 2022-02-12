package com.beleavemebe.chrono.db.converters

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import java.util.*

@TypeConverters
class Converters {
    @TypeConverter
    fun toDate(ms: Long): Date {
        return Date(ms)
    }

    @TypeConverter
    fun fromDate(date: Date): Long {
        return date.time
    }
}
