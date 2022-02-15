package com.beleavemebe.chrono.ui.chrono.recycler

import com.beleavemebe.chrono.model.ChronoEntry
import java.util.*

sealed class Displayable(val viewType: Int) {
    class Entry(val chronoEntry: ChronoEntry) : Displayable(ChronoAdapter.VIEW_TYPE_CHRONO_ENTRY)
    class DateHeader(val date: Date) : Displayable(ChronoAdapter.VIEW_TYPE_DATE_HEADER)
}
