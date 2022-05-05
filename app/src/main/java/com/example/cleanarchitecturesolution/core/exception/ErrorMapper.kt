package com.example.cleanarchitecturesolution.core.exception

import android.content.Context
import androidx.annotation.StringRes
import com.example.cleanarchitecturesolution.R
import com.example.cleanarchitecturesolution.core.exception.ServerException.BadRequest
import com.example.cleanarchitecturesolution.core.exception.ServerException.Internal

interface ErrorMapper {
    fun map(throwable: Throwable): String
}

class ErrorMapperImpl(private val context: Context) : ErrorMapper {
    override fun map(throwable: Throwable): String {
        return when (throwable) {
            is ServerException -> mapServerException(throwable)
            else -> getMessage(R.string.error_unknown)
        }
    }

    private fun mapServerException(serverException: ServerException): String {
        return when (serverException) {
            is Internal -> getMessage(R.string.error_internal)
            is BadRequest -> getMessage(R.string.error_bad_request)
            else -> getMessage(R.string.error_unknown)
        }
    }

    private fun getMessage(@StringRes resource: Int) = context.getString(resource)
}
