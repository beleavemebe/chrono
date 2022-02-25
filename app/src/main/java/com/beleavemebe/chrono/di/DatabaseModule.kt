package com.beleavemebe.chrono.di

import android.content.Context
import androidx.room.Room
import com.beleavemebe.chrono.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

const val DATABASE_NAME = "chrono.db"

@Module
@InstallIn(SingletonComponent::class)
class DataModule {
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            DATABASE_NAME
        ).build()
    }
}
