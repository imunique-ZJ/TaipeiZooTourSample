package zj.app.taipeizootour.api

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import zj.app.taipeizootour.api.converter.TaipeiOpenDataCsvConverterFactory
import zj.app.taipeizootour.api.data.DataSetMetadata
import zj.app.taipeizootour.api.data.ApiResponse
import zj.app.taipeizootour.api.data.csv.AreaIntro
import zj.app.taipeizootour.api.data.Response
import zj.app.taipeizootour.api.data.json.Plant

class TaipeiOpenDataApi : ITaipeiOpenDataApi {

    private val openDataService = Retrofit.Builder()
        .baseUrl("https://data.taipei/api/")
        .addCallAdapterFactory(CallAdapterFactory())
        .addConverterFactory(TaipeiOpenDataCsvConverterFactory.create())
        .addConverterFactory(JacksonConverterFactory.create(ObjectMapper().registerKotlinModule()))
        .build()
        .create(ITaipeiOpenDataService::class.java)

    override suspend fun getMeta(query: String): ApiResponse<Response<DataSetMetadata>>? {
        return withContext(Dispatchers.IO) {
            openDataService.getMeta(query)
        }
    }

    override suspend fun getAreaIntroCsv(id: String, rid: String): ApiResponse<List<AreaIntro>?>? {
        return withContext(Dispatchers.IO) {
            openDataService.getAreaIntroCsv(id, rid)
        }
    }

    override suspend fun getPlantsJson(id: String, offset: Int, limit: Int): ApiResponse<Response<Plant>>? {
        return withContext(Dispatchers.IO) {
            openDataService.getPlantsJson(id, offset, limit)
        }
    }
}