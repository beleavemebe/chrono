package com.beleavemebe.chrono.ui.chrono

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.beleavemebe.chrono.model.ChronoEntry
import com.beleavemebe.chrono.repository.ChronoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class ChronoViewModel @Inject constructor(
    private val repository: ChronoRepository,
) : ViewModel(), ContainerHost<ChronoState, ChronoSideEffect> {

    override val container = container<ChronoState, ChronoSideEffect>(
        initialState = ChronoState(),
    ) {
        subscribeToRepo()
    }

    private fun subscribeToRepo() = intent {
        repository.getAll().collect { entries ->
            val shouldScrollToBottom = runCatching {
                state.entries.first() != entries.first()
            }
                .getOrDefault(false)

            reduce {
                state.copy(isLoading = false, entries = entries)
            }

            if (shouldScrollToBottom) {
                postSideEffect(ChronoSideEffect.ScrollToBottom)
            }
        }
    }

    fun addEntry() = intent {
        postSideEffect(ChronoSideEffect.AddEntry)
    }

    fun editEntry(chronoEntry: ChronoEntry) = intent {
        postSideEffect(ChronoSideEffect.EditEntry(chronoEntry))
    }
}
