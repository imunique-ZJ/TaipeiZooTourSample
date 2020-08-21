package zj.app.taipeizootour.api

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import zj.app.taipeizootour.api.data.DataSetMetadata
import zj.app.taipeizootour.api.data.ApiResponse
import zj.app.taipeizootour.api.data.csv.AreaIntro
import zj.app.taipeizootour.api.data.Response
import zj.app.taipeizootour.api.data.json.Animal
import zj.app.taipeizootour.api.data.json.Plant

interface ITaipeiOpenDataService {

    @GET("https://data.taipei/opendata/datalist/apiAccess?scope=datasetMetadataSearch")
    suspend fun getMeta(@Query("q") query: String): ApiResponse<Response<DataSetMetadata>>?

    @GET("getDatasetInfo/downloadResource")
    suspend fun getAreaIntroCsv(@Query("id") id: String,
                                @Query("rid") rid: String): ApiResponse<List<AreaIntro>?>?

    @GET("v1/dataset/{rid}?scope=resourceAquire")
    suspend fun getPlantsJson(@Path("rid") rid: String,
                              @Query("offset") offset: Int,
                              @Query("limit") limit: Int): ApiResponse<Response<Plant>>


    @GET("v1/dataset/{rid}?scope=resourceAquire")
    suspend fun getAnimalsJson(@Path("rid") rid: String,
                               @Query("offset") offset: Int,
                               @Query("limit") limit: Int): ApiResponse<Response<Animal>>
}