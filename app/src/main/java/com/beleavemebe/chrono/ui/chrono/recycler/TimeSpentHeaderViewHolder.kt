package com.beleavemebe.chrono.ui.chrono.recycler

import androidx.recyclerview.widget.RecyclerView
import com.beleavemebe.chrono.R
import com.beleavemebe.chrono.databinding.ListItemTimeSpentHeaderBinding
import com.beleavemebe.chrono.util.HOUR
import com.beleavemebe.chrono.util.MINUTE
import com.beleavemebe.chrono.util.paint

class TimeSpentHeaderViewHolder(
    private val binding: ListItemTimeSpentHeaderBinding,
    private val timelineViewType: Int,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(timeSpent: Long) {
        binding.timelineView.paint(R.color.black, timelineViewType)
        val differenceText = binding.root.context
            .getString(
                R.string.hour_minute_placeholder,
                timeSpent / HOUR,
                timeSpent % HOUR / MINUTE
            )
        binding.differenceTextView.text = differenceText
    }
}
