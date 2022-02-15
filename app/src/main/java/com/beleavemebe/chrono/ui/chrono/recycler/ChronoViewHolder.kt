package com.beleavemebe.chrono.ui.chrono.recycler

import android.text.format.DateFormat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.beleavemebe.chrono.R
import com.beleavemebe.chrono.databinding.ListItemChronoEntryBinding
import com.beleavemebe.chrono.model.ChronoEntry
import com.beleavemebe.chrono.util.paint

private const val TIME_PATTERN = "HH:mm"

class ChronoViewHolder(
    private val binding: ListItemChronoEntryBinding,
    private val timelineViewType: Int,
    private val onEntryClicked: (ChronoEntry) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(entry: ChronoEntry) {
        binding.titleTextView.text = entry.text
        binding.timeTextView.text = DateFormat.format(TIME_PATTERN, entry.date)
        binding.timelineView.apply {
            paint(R.color.black, timelineViewType)
            setMarkerColor(ContextCompat.getColor(context, R.color.primary))
        }

        binding.root.setOnClickListener {
            onEntryClicked(entry)
        }
    }
}
