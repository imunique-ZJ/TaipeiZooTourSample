package zj.app.taipeizootour.api.converter

import com.fasterxml.jackson.databind.JavaType
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.ObjectReader
import okhttp3.ResponseBody
import retrofit2.Converter
import java.io.IOException
import java.io.InputStreamReader
import java.nio.charset.Charset

internal class TaipeiOpenDataCsvResponseBodyConverter<T>(
    private val adapter: ObjectReader,
    private val typeRef: JavaType,
    private val charset: Charset = Charset.forName("Big5")
) : Converter<ResponseBody, T> {

    @Throws(IOException::class)
    override fun convert(value: ResponseBody): T? {
        return value.use {
            val data = InputStreamReader(it.byteStream(), charset)
            val content = data.readText()
            val maps = adapter.readValues<Map<String, String?>>(content).readAll()
            val json = ObjectMapper().writeValueAsString(maps)
            ObjectMapper().readValue(json, typeRef)
        }
    }

}