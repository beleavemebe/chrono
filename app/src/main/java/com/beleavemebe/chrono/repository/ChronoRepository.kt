package com.beleavemebe.chrono.repository

import com.beleavemebe.chrono.db.AppDatabase
import com.beleavemebe.chrono.model.ChronoEntry
import java.util.*

class ChronoRepository {
    private val dao = AppDatabase.instance.chronoDao()

    fun getAll() = dao.getAll()

    suspend fun getById(id: UUID) = dao.getById(id)

    suspend fun insert(entry: ChronoEntry) = dao.insert(entry)

    suspend fun update(entry: ChronoEntry) = dao.update(entry)
}
