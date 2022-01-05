package com.beleavemebe.chrono.ui

import android.graphics.Color
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.beleavemebe.chrono.databinding.ListItemChronoEntryBinding
import com.beleavemebe.chrono.model.ChronoEntry
import com.github.vipulasri.timelineview.TimelineView
import com.beleavemebe.chrono.databinding.ListItemDateHeaderBinding
import java.lang.IllegalArgumentException
import java.util.*

class ChronoAdapter(
    entries: List<ChronoEntry>,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private sealed class Displayable(val viewType: Int) {
        class Entry(val chronoEntry: ChronoEntry) : Displayable(VIEW_TYPE_CHRONO_ENTRY)
        class DateHeader(val date: Date) : Displayable(VIEW_TYPE_DATE_HEADER)
    }

    private val items = mutableListOf<Displayable>()

    init {
        items.addAll(entries.toDisplayableList())
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        val timeLineViewType = TimelineView.getTimeLineViewType(position, itemCount)
        val displayableViewType = items[position].viewType

        return timeLineViewType + displayableViewType
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val timelineViewType = viewType % DISPLAYABLE_VIEW_TYPE_MULTIPLE
        val displayableViewType = viewType - timelineViewType

        return when (displayableViewType) {
            VIEW_TYPE_CHRONO_ENTRY -> createChronoHolder(inflater, parent, timelineViewType)
            VIEW_TYPE_DATE_HEADER -> createDateHeaderHolder(inflater, parent, timelineViewType)
            else -> throw IllegalArgumentException("Unable to determine view type $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = items[position]) {
            is Displayable.Entry -> bindChronoHolder(holder as ChronoViewHolder, item)
            is Displayable.DateHeader -> bindDateHeaderHolder(holder as DateHeaderViewHolder, item)
        }
    }

    private fun createChronoHolder(inflater: LayoutInflater, parent: ViewGroup, timelineViewType: Int): ChronoViewHolder {
        val binding = ListItemChronoEntryBinding.inflate(inflater, parent, false)
        return ChronoViewHolder(binding, timelineViewType)
    }

    private fun bindChronoHolder(
        holder: ChronoViewHolder,
        entry: Displayable.Entry,
    ) {
        holder.bind(entry.chronoEntry)
    }

    private class ChronoViewHolder(
        private val binding: ListItemChronoEntryBinding,
        private val timelineViewType: Int,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(entry: ChronoEntry) {
            binding.titleTextView.text = entry.title
            binding.timeTextView.text = DateFormat.format(DATE_PATTERN, entry.date)

            val lineColor = Color.BLACK
            binding.timelineView.setEndLineColor(lineColor, timelineViewType)
            binding.timelineView.setStartLineColor(lineColor, timelineViewType)
        }

        companion object {
            private const val DATE_PATTERN = "HH:mm"
        }
    }

    private fun createDateHeaderHolder(
        inflater: LayoutInflater,
        parent: ViewGroup,
        timelineViewType: Int,
    ): DateHeaderViewHolder {
        val binding = ListItemDateHeaderBinding.inflate(inflater, parent, false)
        return DateHeaderViewHolder(binding, timelineViewType)
    }

    private fun bindDateHeaderHolder(
        holder: DateHeaderViewHolder,
        dateHeader: Displayable.DateHeader,
    ) {
        holder.bind(dateHeader.date)
    }

    private class DateHeaderViewHolder(
        private val binding: ListItemDateHeaderBinding,
        private val timelineViewType: Int,
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(date: Date) {
            binding.dateTextView.text = DateFormat.format(DATE_PATTERN, date)

            val lineColor = Color.BLACK
            binding.timelineView.setEndLineColor(lineColor, timelineViewType)
            binding.timelineView.setStartLineColor(lineColor, timelineViewType)
        }

        companion object {
            private const val DATE_PATTERN = "EEE, dd MMM"
        }
    }

    private fun List<ChronoEntry>.toDisplayableList(): List<Displayable> {
        val groupedByDate: Map<Date, List<ChronoEntry>> =
            this.groupBy { chronoEntry -> chronoEntry.determineCalendarDate() }

        return groupedByDate.keys.flatMap { date ->
            val section = mutableListOf<Displayable>()

            section += Displayable.DateHeader(date)
            section += groupedByDate[date]
                ?.sortedBy { it.date }
                ?.map { Displayable.Entry(it) }
                ?: emptyList()

            section
        }
    }

    private fun ChronoEntry.determineCalendarDate(): Date {
        return Calendar.getInstance()
            .run {
                time = date
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
                time
            }
    }

    companion object {
        // Must be 10 or higher since first rank is defined by TimeLineView.getTimeLineViewType()
        private const val DISPLAYABLE_VIEW_TYPE_MULTIPLE = 10
        private const val VIEW_TYPE_CHRONO_ENTRY = 1 * DISPLAYABLE_VIEW_TYPE_MULTIPLE
        private const val VIEW_TYPE_DATE_HEADER = 2 * DISPLAYABLE_VIEW_TYPE_MULTIPLE
    }
}