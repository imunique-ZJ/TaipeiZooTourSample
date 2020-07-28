package zj.app.taipeizootour.api

import zj.app.taipeizootour.api.data.DataSetMetadata
import zj.app.taipeizootour.api.data.ApiResponse
import zj.app.taipeizootour.api.data.Response

interface ITaipeiOpenDataApi {
    suspend fun getMeta(query: String): ApiResponse<Response<DataSetMetadata>>?
}