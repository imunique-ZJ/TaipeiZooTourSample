package zj.app.taipeizootour.api

import retrofit2.http.GET
import retrofit2.http.Query
import zj.app.taipeizootour.api.data.DataSetMetadata
import zj.app.taipeizootour.api.data.ApiResponse
import zj.app.taipeizootour.api.data.csv.AreaIntro
import zj.app.taipeizootour.api.data.Response

interface ITaipeiOpenDataService {

    @GET("https://data.taipei/opendata/datalist/apiAccess?scope=datasetMetadataSearch")
    suspend fun getMeta(@Query("q") query: String): ApiResponse<Response<DataSetMetadata>>?

    @GET("getDatasetInfo/downloadResource")
    suspend fun getCsv(@Query("id") id: String, @Query("rid") rid: String): ApiResponse<List<AreaIntro>?>?
}