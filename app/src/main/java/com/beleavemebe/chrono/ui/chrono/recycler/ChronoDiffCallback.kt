package com.beleavemebe.chrono.ui.chrono.recycler

import androidx.recyclerview.widget.DiffUtil

object ChronoDiffCallback : DiffUtil.ItemCallback<ChronoListItem>() {
    override fun areItemsTheSame(oldItem: ChronoListItem, newItem: ChronoListItem): Boolean {
        return when (oldItem) {
            is ChronoListItem.Entry -> newItem is ChronoListItem.Entry && newItem.chronoEntry.id == oldItem.chronoEntry.id
            is ChronoListItem.DateHeader -> newItem is ChronoListItem.DateHeader && newItem.date == oldItem.date
            is ChronoListItem.TimeSpentHeader -> newItem is ChronoListItem.TimeSpentHeader && newItem.timeSpent == oldItem.timeSpent
        }
    }

    override fun areContentsTheSame(oldItem: ChronoListItem, newItem: ChronoListItem): Boolean {
        return when (oldItem) {
            is ChronoListItem.Entry -> newItem is ChronoListItem.Entry && newItem.chronoEntry == oldItem.chronoEntry
            is ChronoListItem.DateHeader -> areItemsTheSame(oldItem, newItem)
            is ChronoListItem.TimeSpentHeader -> areItemsTheSame(oldItem, newItem)
        }
    }
}
