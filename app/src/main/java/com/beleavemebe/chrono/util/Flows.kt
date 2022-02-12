package com.beleavemebe.chrono.util

import androidx.lifecycle.LifecycleCoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect

fun <T> Flow<T>.launchWhenStarted(scope: LifecycleCoroutineScope) {
    scope.launchWhenStarted {
        this@launchWhenStarted.collect()
    }
}
