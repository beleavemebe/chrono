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
import by.kirich1409.viewbindingdelegate.viewBinding
import com.beleavemebe.chrono.R
import com.beleavemebe.chrono.databinding.FragmentChronoBinding
import com.beleavemebe.chrono.model.ChronoEntry
import com.beleavemebe.chrono.ui.chrono.addedit.AddChronoEntryDialog
import com.beleavemebe.chrono.ui.chrono.addedit.AddEditChronoEntryDialog
import com.beleavemebe.chrono.ui.chrono.addedit.EditChronoEntryDialog
import com.beleavemebe.chrono.ui.chrono.recycler.ChronoAdapter
import com.beleavemebe.chrono.util.launchWhenStarted
import kotlinx.coroutines.flow.onEach

class ChronoFragment : Fragment(R.layout.fragment_chrono) {
    private val binding by viewBinding(FragmentChronoBinding::bind)
    private val viewModel by viewModels<ChronoViewModel> {
        ChronoViewModel.factory()
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
                addEntry()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeToViewModel()
    }

    private fun subscribeToViewModel() {
        viewModel.entries.onEach { entries ->
            binding.tvNoChronology.isVisible = entries.isEmpty()
            fillRecyclerView(entries)
        }.launchWhenStarted(viewLifecycleOwner.lifecycleScope)
    }

    private fun fillRecyclerView(list: List<ChronoEntry>) {
        binding.chronoRecyclerView.adapter =
            ChronoAdapter(list, lifecycleScope, ::editEntry)
    }

    private fun addEntry() {
        val dialog = AddChronoEntryDialog()
        dialog.show(childFragmentManager, AddEditChronoEntryDialog.TAG)
    }

    private fun editEntry(chronoEntry: ChronoEntry) {
        val dialog = EditChronoEntryDialog(chronoEntry)
        dialog.show(childFragmentManager, AddEditChronoEntryDialog.TAG)
    }
}
