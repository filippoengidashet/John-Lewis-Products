package com.filippoengidashet.johnlewis.data.model

/**
 * @author Filippo Engidashet
 * @version 1.0.0
 * @since Tue, 08/02/2022 at 14:19.
 */
sealed class ApiResult<out T> {
    data class Error(val error: Throwable) : ApiResult<Nothing>()
    data class Success<T>(val data: T) : ApiResult<T>()
}
