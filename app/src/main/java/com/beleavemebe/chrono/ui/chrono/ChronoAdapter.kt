package com.beleavemebe.chrono.ui.chrono

import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.beleavemebe.chrono.R
import com.beleavemebe.chrono.databinding.ListItemChronoEntryBinding
import com.beleavemebe.chrono.databinding.ListItemDateHeaderBinding
import com.beleavemebe.chrono.model.ChronoEntry
import com.github.vipulasri.timelineview.TimelineView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

private const val DATE_PATTERN = "EEE, dd MMM"
private const val TIME_PATTERN = "HH:mm"

class ChronoAdapter(
    entries: List<ChronoEntry>,
    scope: CoroutineScope,
    val onEntryClicked: (ChronoEntry) -> Unit,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private sealed class Displayable(val viewType: Int) {
        class Entry(val chronoEntry: ChronoEntry) : Displayable(VIEW_TYPE_CHRONO_ENTRY)
        class DateHeader(val date: Date) : Displayable(VIEW_TYPE_DATE_HEADER)
    }

    private val items = mutableListOf<Displayable>()

    init {
        scope.launch(Dispatchers.Default) {
            items.addAll(entries.toDisplayableList())
        }
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
        val timelineViewType = viewType % DISPLAYABLE_VIEW_TYPE_MULTIPLIER
        val displayableViewType = viewType - timelineViewType

        return when (displayableViewType) {
            VIEW_TYPE_CHRONO_ENTRY -> createChronoHolder(inflater, parent, timelineViewType)
            VIEW_TYPE_DATE_HEADER -> createDateHeaderHolder(inflater, parent, timelineViewType)
            else -> throw IllegalArgumentException("Unable to recognize view type $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = items[position]) {
            is Displayable.Entry -> bindChronoHolder(holder as ChronoViewHolder, item)
            is Displayable.DateHeader -> bindDateHeaderHolder(holder as DateHeaderViewHolder, item)
        }
    }

    private fun createChronoHolder(
        inflater: LayoutInflater,
        parent: ViewGroup,
        timelineViewType: Int,
    ): ChronoViewHolder {
        val binding = ListItemChronoEntryBinding.inflate(inflater, parent, false)
        return ChronoViewHolder(binding, timelineViewType)
    }

    private fun bindChronoHolder(
        holder: ChronoViewHolder,
        entry: Displayable.Entry,
    ) {
        holder.bind(entry.chronoEntry)
    }

    inner class ChronoViewHolder(
        private val binding: ListItemChronoEntryBinding,
        private val timelineViewType: Int,
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

    private fun List<ChronoEntry>.toDisplayableList(): List<Displayable> {
        val groupedByDate: Map<Date, List<ChronoEntry>> =
            groupBy { chronoEntry ->
                chronoEntry.determineCalendarDate()
            }.toSortedMap()

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
        // Must be 10 or higher since last digit is defined by TimeLineView.getTimeLineViewType()
        private const val DISPLAYABLE_VIEW_TYPE_MULTIPLIER = 10
        private const val VIEW_TYPE_CHRONO_ENTRY = 1 * DISPLAYABLE_VIEW_TYPE_MULTIPLIER
        private const val VIEW_TYPE_DATE_HEADER = 2 * DISPLAYABLE_VIEW_TYPE_MULTIPLIER
    }
}

private fun TimelineView.paint(@ColorRes color: Int, viewType: Int) {
    val c = context ?: return
    setStartLineColor(ContextCompat.getColor(c, color), viewType)
    setEndLineColor(ContextCompat.getColor(c, color), viewType)
}
