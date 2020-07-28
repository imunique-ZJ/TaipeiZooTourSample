package zj.app.taipeizootour.api

import retrofit2.http.GET
import retrofit2.http.Query
import zj.app.taipeizootour.api.data.DataSetMetadata
import zj.app.taipeizootour.api.data.ApiResponse
import zj.app.taipeizootour.api.data.Response

interface ITaipeiOpenDataService {

    @GET("https://data.taipei/opendata/datalist/apiAccess?scope=datasetMetadataSearch")
    suspend fun getMeta(@Query("q") query: String): ApiResponse<Response<DataSetMetadata>>?

}