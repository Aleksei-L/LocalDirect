package com.localdirect.core

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

fun CoroutineScope.safetyLaunch(
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    logging: Boolean = true,
    block: suspend CoroutineScope.() -> Unit
) = launch(context, start) {
    try {
        block()
    } catch (e: CancellationException) {
        if (logging)
            Timber.w("Coroutine in context $coroutineContext was cancelled: $e")
    } catch (e: Exception) {
        if (logging) {
            Timber.e("Something went wrong with coroutine in context $coroutineContext: $e")
            Timber.e(e.stackTraceToString())
        }
    }
}