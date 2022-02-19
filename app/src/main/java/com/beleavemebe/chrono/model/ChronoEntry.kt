package com.beleavemebe.chrono.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "chrono")
data class ChronoEntry(
    @PrimaryKey val id: UUID = UUID.randomUUID(),
    val text: String = "",
    val date: Date = Date(),
)
