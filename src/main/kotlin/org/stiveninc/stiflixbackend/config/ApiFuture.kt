package org.stiveninc.stiflixbackend.config

import com.google.api.core.ApiFuture
import com.google.api.core.ApiFutureCallback
import com.google.api.core.ApiFutures
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import java.util.concurrent.Executor
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

private val DIRECT_EXECUTOR = Executor { command -> command.run() }

/**
 * Extension function to convert a Firestore ApiFuture to a Kotlin Coroutine
 */
suspend fun <T> ApiFuture<T>.await(): T {
    if (isDone) {
        try {
            return withContext(Dispatchers.IO) {
                get()
            }
        } catch (e: Exception) {
            throw e.cause ?: e
        }
    }

    return suspendCancellableCoroutine { cont ->
        ApiFutures.addCallback(this, object : ApiFutureCallback<T> {
            override fun onSuccess(result: T?) {
                cont.resume(result as T)
            }

            override fun onFailure(t: Throwable) {
                cont.resumeWithException(t)
            }
        }, DIRECT_EXECUTOR)

        // If the coroutine is cancelled, cancel the future too
        cont.invokeOnCancellation {
            cancel(false)
        }
    }
}