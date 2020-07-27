package zj.app.taipeizootour.api.data

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

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