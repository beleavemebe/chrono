@file:Suppress("FunctionName")

package com.beleavemebe.chrono.ui.chrono.addedit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import com.beleavemebe.chrono.R
import com.beleavemebe.chrono.databinding.DialogAddEditChronoEntryBinding
import com.beleavemebe.chrono.model.ChronoEntry
import com.beleavemebe.chrono.ui.chrono.addedit.AddEditChronoEntryDialog.Companion.ARG_ENTRY_ID
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import org.orbitmvi.orbit.viewmodel.observe
import java.util.*

@AndroidEntryPoint
class AddEditChronoEntryDialog : BottomSheetDialogFragment() {
    private lateinit var binding: DialogAddEditChronoEntryBinding

    private val viewModel by lazy {
        val viewModel: AddEditChronoEntryViewModel by viewModels()
        viewModel.apply {
            entryId = arguments?.get(ARG_ENTRY_ID) as? UUID
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = DialogAddEditChronoEntryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeToViewModel()
    }

    private fun subscribeToViewModel() {
        viewModel.observe(
            lifecycleOwner = viewLifecycleOwner,
            state = ::renderState,
        )
    }

    private fun renderState(state: AddEditChronoState) {
        val entry = state.chronoEntry ?: return
        initListeners(entry)
        renderUi(entry)
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
