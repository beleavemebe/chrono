package com.beleavemebe.chrono.ui.chrono.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.beleavemebe.chrono.databinding.ListItemChronoEntryBinding
import com.beleavemebe.chrono.databinding.ListItemDateHeaderBinding
import com.beleavemebe.chrono.databinding.ListItemTimeSpentHeaderBinding
import com.beleavemebe.chrono.model.ChronoEntry
import com.github.vipulasri.timelineview.TimelineView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ChronoAdapter(
    private val scope: CoroutineScope,
    private val onEntryClicked: (ChronoEntry) -> Unit,
) : ListAdapter<ChronoListItem, RecyclerView.ViewHolder>(ChronoDiffCallback) {
    fun setEntries(entries: List<ChronoEntry>) {
        scope.launch(Dispatchers.Default) {
            val items = entries.toChronoListItems()
            withContext(Dispatchers.Main) {
                submitList(items)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        val timelineViewType = TimelineView.getTimeLineViewType(position, itemCount)
        val viewType = getItem(position).viewType

        return timelineViewType + viewType
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val timelineViewType = viewType % VIEW_TYPE_MULTIPLIER
        val itemViewType = viewType - timelineViewType

        return when (itemViewType) {
            VIEW_TYPE_CHRONO_ENTRY -> createChronoHolder(inflater, parent, timelineViewType)
            VIEW_TYPE_DATE_HEADER -> createDateHeaderHolder(inflater, parent, timelineViewType)
            VIEW_TYPE_TIME_SPENT_HEADER -> createTimeSpentHeaderHolder(inflater, parent, timelineViewType)
            else -> throw IllegalArgumentException("Unable to recognize view type $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) =
        when (val item = getItem(position)) {
            is ChronoListItem.Entry -> (holder as ChronoViewHolder).bind(item.chronoEntry)
            is ChronoListItem.DateHeader -> (holder as DateHeaderViewHolder).bind(item.date)
            is ChronoListItem.TimeSpentHeader -> (holder as TimeSpentHeaderViewHolder).bind(item.timeSpent)
        }

    private fun createChronoHolder(
        inflater: LayoutInflater,
        parent: ViewGroup,
        timelineViewType: Int,
    ): ChronoViewHolder {
        val binding = ListItemChronoEntryBinding.inflate(inflater, parent, false)
        return ChronoViewHolder(binding, timelineViewType, onEntryClicked)
    }

    private fun createDateHeaderHolder(
        inflater: LayoutInflater,
        parent: ViewGroup,
        timelineViewType: Int,
    ): DateHeaderViewHolder {
        val binding = ListItemDateHeaderBinding.inflate(inflater, parent, false)
        return DateHeaderViewHolder(binding, timelineViewType)
    }

    private fun createTimeSpentHeaderHolder(
        inflater: LayoutInflater,
        parent: ViewGroup,
        timelineViewType: Int
    ): TimeSpentHeaderViewHolder {
        val binding = ListItemTimeSpentHeaderBinding.inflate(inflater, parent, false)
        return TimeSpentHeaderViewHolder(binding, timelineViewType)
    }

    companion object {
        // Must be 10 or higher since last digit is defined by TimeLineView.getTimeLineViewType()
        const val VIEW_TYPE_MULTIPLIER = 10
        const val VIEW_TYPE_CHRONO_ENTRY = 1 * VIEW_TYPE_MULTIPLIER
        const val VIEW_TYPE_DATE_HEADER = 2 * VIEW_TYPE_MULTIPLIER
        const val VIEW_TYPE_TIME_SPENT_HEADER = 3 * VIEW_TYPE_MULTIPLIER
    }
}
