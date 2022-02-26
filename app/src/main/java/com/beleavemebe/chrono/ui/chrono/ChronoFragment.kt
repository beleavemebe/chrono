package com.beleavemebe.chrono.ui.chrono

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.beleavemebe.chrono.R
import com.beleavemebe.chrono.databinding.FragmentChronoBinding
import com.beleavemebe.chrono.model.ChronoEntry
import com.beleavemebe.chrono.ui.chrono.recycler.ChronoAdapter
import dagger.hilt.android.AndroidEntryPoint
import org.orbitmvi.orbit.viewmodel.observe

@AndroidEntryPoint
class ChronoFragment : Fragment(R.layout.fragment_chrono) {
    private val binding by viewBinding(FragmentChronoBinding::bind)

    private val viewModel: ChronoViewModel by viewModels()

    private val adapter by lazy {
        ChronoAdapter(viewLifecycleOwner.lifecycleScope, viewModel::editEntry)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_chrono, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.new_entry -> {
                viewModel.addEntry()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        subscribeToViewModel()
    }

    private fun initRecyclerView() {
        binding.chronoRecyclerView.adapter = adapter
    }

    private fun subscribeToViewModel() {
        viewModel.observe(
            lifecycleOwner = viewLifecycleOwner,
            state = ::renderState,
            sideEffect = ::handleSideEffect
        )
    }

    private fun renderState(state: ChronoState) {
        binding.progressBar.isVisible = state.isLoading
        binding.tvNoChronology.isVisible = !state.isLoading
        binding.chronoRecyclerView.isVisible = !state.isLoading
        if (!state.isLoading) {
            binding.tvNoChronology.isVisible = state.entries.isEmpty()
            adapter.setEntries(state.entries)
        }
    }

    private fun handleSideEffect(sideEffect: ChronoSideEffect) =
        when (sideEffect) {
            is ChronoSideEffect.ScrollToBottom -> scrollToBottom()
            is ChronoSideEffect.AddEntry -> showNewEntryDialog()
            is ChronoSideEffect.EditEntry -> showEditEntryDialog(sideEffect.chronoEntry)
        }

    private fun scrollToBottom() {
        val count = adapter.itemCount
        binding.chronoRecyclerView.smoothScrollToPosition(count)
    }

    private fun showNewEntryDialog() {
        findNavController().navigate(
            ChronoFragmentDirections.actionFragmentChronoToDialogAddEditChronoEntry(
                null
            )
        )
    }

    private fun showEditEntryDialog(chronoEntry: ChronoEntry) {
        findNavController().navigate(
            ChronoFragmentDirections.actionFragmentChronoToDialogAddEditChronoEntry(
                chronoEntry.id
            )
        )
    }
}
