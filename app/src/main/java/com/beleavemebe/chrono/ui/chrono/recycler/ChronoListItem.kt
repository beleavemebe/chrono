package com.beleavemebe.chrono.ui.chrono.recycler

import com.beleavemebe.chrono.model.ChronoEntry
import java.util.*

sealed class ChronoListItem(val viewType: Int) {
    data class Entry(val chronoEntry: ChronoEntry) : ChronoListItem(ChronoAdapter.VIEW_TYPE_CHRONO_ENTRY)
    data class DateHeader(val date: Date) : ChronoListItem(ChronoAdapter.VIEW_TYPE_DATE_HEADER)
    data class TimeSpentHeader(val timeSpent: Long) : ChronoListItem(ChronoAdapter.VIEW_TYPE_TIME_SPENT_HEADER)
}
