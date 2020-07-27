package zj.app.taipeizootour.api

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import zj.app.taipeizootour.api.data.DataSetMetadata
import zj.app.taipeizootour.api.data.Response

object TaipeiOpenDataApi: ITaipeiOpenDataApi {

    private val openDataService = Retrofit.Builder()
        .baseUrl("https://data.taipei/api/")
        .addConverterFactory(JacksonConverterFactory.create(ObjectMapper().registerKotlinModule()))
        .build()
        .create(ITaipeiOpenDataService::class.java)

    override suspend fun getMeta(query: String): Response<DataSetMetadata>? {
        return withContext(Dispatchers.IO) {
            openDataService.getMeta(query)
        }
    }
}