package com.beleavemebe.chrono.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "chrono")
data class ChronoEntry(
    val text: String = "",
    val date: Date = Date(),
) {
    @PrimaryKey var id: UUID = UUID.randomUUID()
}
