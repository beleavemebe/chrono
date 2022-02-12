package com.beleavemebe.chrono.db.diary

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.beleavemebe.chrono.model.DiaryEntry
import kotlinx.coroutines.flow.Flow

@Dao
interface DiaryDao {
    @Query("SELECT * FROM diary ORDER BY date DESC")
    fun getAll(): Flow<List<DiaryEntry>>

    @Insert
    suspend fun insert(entry: DiaryEntry)
}
