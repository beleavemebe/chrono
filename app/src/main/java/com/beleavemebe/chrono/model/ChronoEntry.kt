package com.beleavemebe.chrono.model

import java.util.*

data class ChronoEntry(
    val id: UUID = UUID.randomUUID(),
    val title: String,
    val text: String = "",
    val date: Date = Date(),
)