package com.lacaldirect.core

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
    block: suspend CoroutineScope.() -> Unit
) {
    try {
        this.launch(context, start, block)
    } catch (e: CancellationException) {
        Timber.w("Coroutine with context ${this.coroutineContext} was cancelled: $e")
    } catch (e: Exception) {
        Timber.e("Something went wrong with coroutine in context ${this.coroutineContext}: $e")
    }
}