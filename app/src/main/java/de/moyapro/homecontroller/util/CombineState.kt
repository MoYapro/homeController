package de.moyapro.homecontroller.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn


fun <T1, T2, R> combineState(
    flow1: StateFlow<T1>,
    flow2: StateFlow<T2>,
    scope: CoroutineScope = GlobalScope,
    sharingStarted: SharingStarted = SharingStarted.Eagerly,
    transform: (T1, T2) -> R,
): StateFlow<R> = combine(flow1, flow2) { o1, o2 ->
    transform.invoke(o1, o2)
}.stateIn(scope, sharingStarted, transform.invoke(flow1.value, flow2.value))
