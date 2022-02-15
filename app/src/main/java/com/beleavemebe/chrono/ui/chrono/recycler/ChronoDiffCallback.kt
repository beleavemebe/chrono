package com.beleavemebe.chrono.ui.chrono.recycler

import androidx.recyclerview.widget.DiffUtil

object ChronoDiffCallback : DiffUtil.ItemCallback<Displayable>() {
    override fun areItemsTheSame(oldItem: Displayable, newItem: Displayable): Boolean {
        return when (oldItem) {
            is Displayable.DateHeader -> newItem is Displayable.DateHeader && newItem.date == oldItem.date
            is Displayable.Entry -> newItem is Displayable.Entry && newItem.chronoEntry.id == oldItem.chronoEntry.id
        }
    }

    override fun areContentsTheSame(oldItem: Displayable, newItem: Displayable): Boolean {
        return when (oldItem) {
            is Displayable.DateHeader -> newItem is Displayable.DateHeader && newItem.date == oldItem.date
            is Displayable.Entry -> newItem is Displayable.Entry && newItem.chronoEntry == oldItem.chronoEntry
        }
    }
}
