package com.beleavemebe.chrono.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
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

    companion object {
        private const val DATABASE_NAME = "chrono.db"

        lateinit var instance: AppDatabase
            private set

        fun initialize(context: Context) {
            instance = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                DATABASE_NAME
            ).build()
        }
    }
}
