package com.beleavemebe.chrono.ui.chrono.addedit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.beleavemebe.chrono.R
import com.beleavemebe.chrono.databinding.DialogAddEditChronoEntryBinding
import com.beleavemebe.chrono.model.ChronoEntry
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import org.orbitmvi.orbit.viewmodel.observe

@AndroidEntryPoint
class AddEditChronoEntryDialog : BottomSheetDialogFragment() {
    private var _binding: DialogAddEditChronoEntryBinding? = null
    private val binding get() = _binding!!

    private val args: AddEditChronoEntryDialogArgs by navArgs()

    private val viewModel by lazy {
        val viewModel: AddEditChronoEntryViewModel by viewModels()
        viewModel.apply {
            entryId = args.id
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = DialogAddEditChronoEntryBinding
            .inflate(inflater, container, false)

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
