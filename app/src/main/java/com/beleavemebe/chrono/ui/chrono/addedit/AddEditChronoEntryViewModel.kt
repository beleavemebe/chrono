package com.beleavemebe.chrono.ui.chrono.addedit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.beleavemebe.chrono.model.ChronoEntry
import com.beleavemebe.chrono.repository.ChronoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import java.util.*
import javax.inject.Inject

@HiltViewModel
class AddEditChronoEntryViewModel @Inject constructor(
    private val repository: ChronoRepository,
) : ViewModel(), ContainerHost<AddEditChronoState, Nothing> {
    var entryId: UUID? = null

    override val container = container<AddEditChronoState, Nothing>(
        initialState = AddEditChronoState()
    ) {
        fetchEntry()
    }

    private val entryFlow = flow {
        val id = entryId
        val entry = if (id != null) {
            repository.getById(id)
        } else {
            ChronoEntry()
        }

        emit(entry)
    }

    private fun fetchEntry() = intent {
        entryFlow.collect { entry ->
            reduce {
                state.copy(chronoEntry = entry)
            }
        }
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
}
