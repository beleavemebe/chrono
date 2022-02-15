package com.beleavemebe.chrono.util

import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.github.vipulasri.timelineview.TimelineView

fun TimelineView.paint(@ColorRes color: Int, viewType: Int) {
    val c = context ?: return
    setStartLineColor(ContextCompat.getColor(c, color), viewType)
    setEndLineColor(ContextCompat.getColor(c, color), viewType)
}
