package com.beleavemebe.chrono.ui.chrono.addedit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.beleavemebe.chrono.model.ChronoEntry
import com.beleavemebe.chrono.repository.ChronoRepository
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.util.*

class AddEditChronoEntryViewModel(
    private val entryId: UUID?,
    private val repository: ChronoRepository,
) : ViewModel() {

    val entry = flow {
        val entry = if (entryId != null) {
            repository.getById(entryId)
        } else {
            ChronoEntry()
        }

        emit(entry)
    }

    fun saveEntry(entry: ChronoEntry) {
        if (entryId != null) {
            update(entry)
        } else {
            insert(entry)
        }
    }

    private fun insert(entry: ChronoEntry) {
        viewModelScope.launch {
            repository.insert(entry)
        }
    }

    private fun update(entry: ChronoEntry) {
        viewModelScope.launch {
            repository.update(entry)
        }
    }

    companion object {
        fun factory(
            uuid: UUID?
        ) = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return AddEditChronoEntryViewModel(
                    uuid,
                    ChronoRepository(),
                ) as T
            }
        }
    }
}
