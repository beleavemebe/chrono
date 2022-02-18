package com.beleavemebe.chrono.ui.chrono

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.beleavemebe.chrono.model.ChronoEntry
import com.beleavemebe.chrono.repository.ChronoRepository
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container

class ChronoViewModel(
    private val repository: ChronoRepository,
) : ViewModel(), ContainerHost<ChronoState, ChronoSideEffect> {
    override val container = container<ChronoState, ChronoSideEffect>(
        initialState = ChronoState(),
    ) {
        subscribeToRepo()
    }

    private fun subscribeToRepo() = intent {
        repository.getAll().collect { list ->
            reduce {
                Log.d("ChronoViewModel", "got list ${list.hashCode()}")
                state.copy(isLoading = false, entries = list)
            }
        }
    }

    fun addEntry() = intent {
        postSideEffect(ChronoSideEffect.AddEntry)
    }

    fun editEntry(chronoEntry: ChronoEntry) = intent {
        postSideEffect(ChronoSideEffect.EditEntry(chronoEntry))
    }

    companion object {
        @Suppress("UNCHECKED_CAST")
        fun factory() = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return ChronoViewModel(
                    ChronoRepository(),
                ) as T
            }
        }
    }
}
