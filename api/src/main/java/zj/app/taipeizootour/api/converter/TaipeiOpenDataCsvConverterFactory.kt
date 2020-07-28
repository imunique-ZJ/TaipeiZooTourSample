package zj.app.taipeizootour.api.converter

import com.fasterxml.jackson.dataformat.csv.CsvMapper
import com.fasterxml.jackson.dataformat.csv.CsvSchema
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import zj.app.taipeizootour.api.data.csv.ICsvDataType
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class TaipeiOpenDataCsvConverterFactory private constructor(
    private val csvMapper: CsvMapper
): Converter.Factory() {

    companion object {
        fun create(): TaipeiOpenDataCsvConverterFactory {
            return TaipeiOpenDataCsvConverterFactory(CsvMapper().registerKotlinModule() as CsvMapper)
        }
    }

    override fun responseBodyConverter(
        type: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *>? {
        return when (type) {
            is Class<*> -> createConverter(type)
            is ParameterizedType -> {
                (type.actualTypeArguments[0] as? Class<*>)?.let {
                    createConverter(it)
                }
            }
            else -> null
        }
    }

    private fun createConverter(clazz: Class<*>?): TaipeiOpenDataCsvResponseBodyConverter<*>? {
        return clazz?.let {
            if (ICsvDataType::class.java.isAssignableFrom(clazz)) {
                val elementType = csvMapper.typeFactory.constructType(clazz)
                val javaType = csvMapper.typeFactory.constructCollectionType(List::class.java, elementType)
                val schema = CsvSchema.emptySchema()
                    .withHeader()
                    .withColumnSeparator(',')
                val reader = csvMapper.readerFor(Map::class.java).with(schema)
                TaipeiOpenDataCsvResponseBodyConverter<Any?>(reader, javaType)
            } else {
                null
            }
        }
    }
}