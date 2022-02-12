package com.beleavemebe.chrono.db.chrono

import androidx.room.*
import com.beleavemebe.chrono.model.ChronoEntry
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
interface ChronoDao {
    @Query("SELECT * FROM chrono ORDER BY date DESC")
    fun getAll(): Flow<List<ChronoEntry>>

    @Query("SELECT * FROM chrono WHERE id=(:id)")
    suspend fun getById(id: UUID): ChronoEntry

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entry: ChronoEntry)

    @Update
    suspend fun update(entry: ChronoEntry)
}
