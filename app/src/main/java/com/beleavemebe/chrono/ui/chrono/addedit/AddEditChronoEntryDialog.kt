@file:Suppress("FunctionName")

package com.beleavemebe.chrono.ui.chrono.addedit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.beleavemebe.chrono.R
import com.beleavemebe.chrono.databinding.DialogAddEditChronoEntryBinding
import com.beleavemebe.chrono.model.ChronoEntry
import com.beleavemebe.chrono.ui.chrono.addedit.AddEditChronoEntryDialog.Companion.ARG_ENTRY_ID
import com.beleavemebe.chrono.util.launchWhenStarted
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.flow.onEach
import java.util.*

class AddEditChronoEntryDialog : BottomSheetDialogFragment() {
    private lateinit var binding: DialogAddEditChronoEntryBinding
    private val viewModel by viewModels<AddEditChronoEntryViewModel> {
        val id = arguments?.get(ARG_ENTRY_ID) as? UUID
        AddEditChronoEntryViewModel.factory(id)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = DialogAddEditChronoEntryBinding.inflate(inflater, container, false)
        subscribeToViewModel()
        return binding.root
    }

    private fun subscribeToViewModel() {
        viewModel.entry.onEach { entry ->
            initListeners(entry)
            renderUi(entry)
        }.launchWhenStarted(viewLifecycleOwner.lifecycleScope)
    }

    private fun initListeners(entry: ChronoEntry) {
        binding.etEntryText.doOnTextChanged { _, _, _, _ ->
            binding.tiEntryText.error = null
        }

        binding.btnCancel.setOnClickListener {
            dismiss()
        }

        binding.btnSave.setOnClickListener {
            val text = binding.etEntryText.text.toString()
            if (text.isNotBlank()) {
                viewModel.saveEntry(entry.copy(text = text))
                dismiss()
            } else {
                showError()
            }
        }
    }

    private fun showError() {
        binding.tiEntryText.error = getString(R.string.text_is_empty)
    }

    private fun renderUi(entry: ChronoEntry) {
        binding.etEntryText.setText(entry.text)
    }

    companion object {
        const val ARG_ENTRY_ID = "entry_id"
        const val TAG = "AddEditChronoEntryDialog"
    }
}

fun AddChronoEntryDialog() = AddEditChronoEntryDialog()

fun EditChronoEntryDialog(entry: ChronoEntry) = AddEditChronoEntryDialog().apply {
    arguments = bundleOf(ARG_ENTRY_ID to entry.id)
}
