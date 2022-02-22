package com.beleavemebe.chrono.ui.chrono.recycler

import com.beleavemebe.chrono.model.ChronoEntry
import com.beleavemebe.chrono.util.ONE_AND_A_HALF_AN_HOUR
import java.util.*
import kotlin.math.abs

sealed class ChronoListItem(val viewType: Int) {
    data class Entry(val chronoEntry: ChronoEntry) : ChronoListItem(ChronoAdapter.VIEW_TYPE_CHRONO_ENTRY)
    data class DateHeader(val date: Date) : ChronoListItem(ChronoAdapter.VIEW_TYPE_DATE_HEADER)
    data class TimeSpentHeader(val timeSpent: Long) : ChronoListItem(ChronoAdapter.VIEW_TYPE_TIME_SPENT_HEADER)
}

fun List<ChronoEntry>.toChronoListItems(): List<ChronoListItem> {
    fun ChronoEntry.getDate(): Date {
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

    val groupedByDate: Map<Date, List<ChronoEntry>> =
        this.groupBy { chronoEntry ->
            chronoEntry.getDate()
        }.toSortedMap()

    val withDateHeaders = groupedByDate.keys.flatMap { date ->
        val section = mutableListOf<ChronoListItem>()

        section += ChronoListItem.DateHeader(date)
        section += groupedByDate[date]
            ?.sortedBy { it.date }
            ?.map { ChronoListItem.Entry(it) }

            ?: emptyList()

        section
    }

    val withTimeSpentHeaders = withDateHeaders.toMutableList()
    withDateHeaders.forEachIndexed { i, item ->
        if (i == withDateHeaders.lastIndex) {
            return@forEachIndexed
        }

        val next = withDateHeaders[i + 1]
        if (item !is ChronoListItem.Entry || next !is ChronoListItem.Entry) {
            return@forEachIndexed
        }

        val diff = abs(item.chronoEntry.date.time - next.chronoEntry.date.time)
        if (diff > ONE_AND_A_HALF_AN_HOUR) {
            val targetIndex = withTimeSpentHeaders.indexOf(item) + 1
            withTimeSpentHeaders.add(targetIndex, ChronoListItem.TimeSpentHeader(diff))
        }
    }

    return withTimeSpentHeaders
}
