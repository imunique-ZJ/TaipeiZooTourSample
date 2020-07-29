package zj.app.taipeizootour.api

import zj.app.taipeizootour.api.data.DataSetMetadata
import zj.app.taipeizootour.api.data.ApiResponse
import zj.app.taipeizootour.api.data.csv.AreaIntro
import zj.app.taipeizootour.api.data.Response
import zj.app.taipeizootour.api.data.json.Plant

interface ITaipeiOpenDataApi {

    suspend fun getMeta(query: String): ApiResponse<Response<DataSetMetadata>>?
    suspend fun getAreaIntroCsv(id: String, rid: String): ApiResponse<List<AreaIntro>?>?
    suspend fun getPlantsJson(id: String, offset: Int = 0, limit: Int = 1000): ApiResponse<Response<Plant>>?

}