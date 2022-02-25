package com.beleavemebe.chrono.repository

import com.beleavemebe.chrono.db.AppDatabase
import com.beleavemebe.chrono.model.ChronoEntry
import java.util.*
import javax.inject.Inject

class ChronoRepository @Inject constructor(
    database: AppDatabase,
) {
    private val dao = database.chronoDao()

    fun getAll() = dao.getAll()

    suspend fun getById(id: UUID) = dao.getById(id)

    suspend fun insert(entry: ChronoEntry) = dao.insert(entry)

    suspend fun update(entry: ChronoEntry) = dao.update(entry)
}
