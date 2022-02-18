package com.beleavemebe.chrono.ui.chrono

import com.beleavemebe.chrono.model.ChronoEntry

sealed class ChronoSideEffect {
    object AddEntry : ChronoSideEffect()
    data class EditEntry(val chronoEntry: ChronoEntry) : ChronoSideEffect()
}
