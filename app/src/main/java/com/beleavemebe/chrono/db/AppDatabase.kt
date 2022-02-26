package com.beleavemebe.chrono.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.beleavemebe.chrono.db.chrono.ChronoDao
import com.beleavemebe.chrono.db.converters.Converters
import com.beleavemebe.chrono.db.diary.DiaryDao
import com.beleavemebe.chrono.model.ChronoEntry
import com.beleavemebe.chrono.model.DiaryEntry

@Database(entities = [ChronoEntry::class, DiaryEntry::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun chronoDao(): ChronoDao
    abstract fun diaryDao(): DiaryDao
}
