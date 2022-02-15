package com.beleavemebe.chrono.ui.chrono.recycler

import androidx.recyclerview.widget.DiffUtil

object ChronoDiffCallback : DiffUtil.ItemCallback<ChronoListItem>() {
    override fun areItemsTheSame(oldItem: ChronoListItem, newItem: ChronoListItem): Boolean {
        return when (oldItem) {
            is ChronoListItem.DateHeader -> newItem is ChronoListItem.DateHeader && newItem.date == oldItem.date
            is ChronoListItem.Entry -> newItem is ChronoListItem.Entry && newItem.chronoEntry.id == oldItem.chronoEntry.id
        }
    }

    override fun areContentsTheSame(oldItem: ChronoListItem, newItem: ChronoListItem): Boolean {
        return when (oldItem) {
            is ChronoListItem.DateHeader -> newItem is ChronoListItem.DateHeader && newItem.date == oldItem.date
            is ChronoListItem.Entry -> newItem is ChronoListItem.Entry && newItem.chronoEntry == oldItem.chronoEntry
        }
    }
}
