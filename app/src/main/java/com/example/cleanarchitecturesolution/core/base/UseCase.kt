package com.example.cleanarchitecturesolution.core.base

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

abstract class UseCase<out Type, in Params> {

    abstract suspend fun doAction(params: Params): Type

    operator fun invoke(
        coroutineContext: CoroutineContext = Dispatchers.IO + Job(),
        params: Params,
        scope: CoroutineScope,
        onResult: (Result<Type>) -> Unit = {}
    ) {
        scope.launch {
            val result = withContext(coroutineContext) {
                runCatching { doAction(params) }
            }

            onResult(result)
        }
    }
}
