package com.example.cleanarchitecturesolution.core.exception

import com.example.cleanarchitecturesolution.core.exception.ServerException.*
import retrofit2.HttpException

interface ErrorWrapper {
    fun wrap(throwable: Throwable): Throwable
}

class ErrorWrapperImpl : ErrorWrapper {
    override fun wrap(throwable: Throwable): Throwable {
        return when (throwable) {
            is HttpException -> wrapServerError(throwable)
            else -> throwable
        }
    }

    private fun wrapServerError(httpException: HttpException): Throwable {
        return with(httpException) {
            when (code()) {
                INTERNAL_SERVER_ERROR -> Internal(message())
                BAD_REQUEST -> BadRequest(message())
                else -> Unknown(message())
            }
        }
    }

    private companion object {
        const val BAD_REQUEST = 400
        const val INTERNAL_SERVER_ERROR = 500
    }
}

suspend fun <T> callOrThrow(
    errorWrapper: ErrorWrapper,
    apiCall: suspend () -> T,
): T {
    return runCatching { apiCall() }
        .getOrElse { throw errorWrapper.wrap(it) }
}
