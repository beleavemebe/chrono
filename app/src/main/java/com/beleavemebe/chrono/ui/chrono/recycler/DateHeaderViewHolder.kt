package com.beleavemebe.chrono.ui.chrono.recycler

import android.text.format.DateFormat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.beleavemebe.chrono.R
import com.beleavemebe.chrono.databinding.ListItemDateHeaderBinding
import com.beleavemebe.chrono.util.paint
import java.util.*

private const val DATE_PATTERN = "EEE, dd MMM"

class DateHeaderViewHolder(
    private val binding: ListItemDateHeaderBinding,
    private val timelineViewType: Int,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(date: Date) {
        binding.dateTextView.text = DateFormat.format(DATE_PATTERN, date).toString()
            .replaceFirstChar(Char::uppercase)

        binding.timelineView.apply {
            paint(R.color.black, timelineViewType)
            setMarkerColor(ContextCompat.getColor(context, R.color.black))
        }
    }
}
