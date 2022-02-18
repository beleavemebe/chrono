package com.beleavemebe.chrono.ui.chrono

import com.beleavemebe.chrono.model.ChronoEntry

data class ChronoState(
    val isLoading: Boolean = true,
    val entries: List<ChronoEntry> = emptyList(),
)
