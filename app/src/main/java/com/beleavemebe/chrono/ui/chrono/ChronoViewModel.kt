package com.beleavemebe.chrono.ui.chrono

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.beleavemebe.chrono.repository.ChronoRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class ChronoViewModel(
    repository: ChronoRepository,
) : ViewModel() {
    val entries = repository.getAll()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    companion object {
        @Suppress("UNCHECKED_CAST")
        fun factory() = object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return ChronoViewModel(
                    ChronoRepository(),
                ) as T
            }
        }
    }
}
