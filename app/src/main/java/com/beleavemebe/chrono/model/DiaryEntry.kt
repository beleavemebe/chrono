package com.beleavemebe.chrono.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "diary")
data class DiaryEntry(
    @PrimaryKey val id: UUID = UUID.randomUUID(),
    val title: String,
    val text: String,
    val date: Date = Date(),
)
