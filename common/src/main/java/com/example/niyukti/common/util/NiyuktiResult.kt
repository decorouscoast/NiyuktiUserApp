package com.example.niyukti.common.util

/**
 * A generic wrapper class for handling API responses and local data operations
 */
sealed class NiyuktiResult<T> {
    abstract val resultCode: String?

    open class Success<T>(
        val data: T,
        override val resultCode: String? = "0",
    ) : NiyuktiResult<T>()

    open class Error<T>(
        override val resultCode: String? = "",
        val message: String? = "",
        val thr: Throwable? = null
    ) : NiyuktiResult<T>()

    fun isError() = this is Error

    fun isSuccess() = this is Success
}
