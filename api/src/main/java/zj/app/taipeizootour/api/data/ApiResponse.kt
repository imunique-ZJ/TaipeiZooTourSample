package zj.app.taipeizootour.api.data

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

sealed class ApiResponse<out T> {
    data class Success<T>(val url: String,
                          val responseCode: Int,
                          val data: T?): ApiResponse<T>()

    data class Failure(val url: String,
                       val responseCode: Int?,
                       val error: Throwable?): ApiResponse<Nothing>()

    object NetworkError : ApiResponse<Nothing>()
}

val <T> ApiResponse<T>.success get() = (this as? ApiResponse.Success)?.data

inline fun <T, R> ApiResponse<T>.then(success: (ApiResponse.Success<T>) -> R?,
                                      fail: (ApiResponse.Failure) -> R?,
                                      noinline networkError: (() -> R)? = null): R? {
    return when (this) {
        is ApiResponse.Success -> success(this)
        is ApiResponse.Failure -> fail(this)
        else -> networkError?.invoke()
    }
}

data class Response<T> (
    val result: OpenDataResponse<T>
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class OpenDataResponse<T> (
    val offset: Int = 0,
    val limit: Int = 0,
    val count: Int = 0,
    val results: List<T>?
)