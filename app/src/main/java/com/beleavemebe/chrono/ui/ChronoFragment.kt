package com.beleavemebe.chrono.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.beleavemebe.chrono.R
import com.beleavemebe.chrono.databinding.FragmentChronoBinding
import com.beleavemebe.chrono.repository.ChronoRepository

class ChronoFragment : Fragment(R.layout.fragment_chrono) {
    private val binding by viewBinding(FragmentChronoBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.chronoRecyclerView.adapter = ChronoAdapter(ChronoRepository.items)
    }
}