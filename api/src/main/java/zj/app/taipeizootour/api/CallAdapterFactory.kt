package zj.app.taipeizootour.api

import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import zj.app.taipeizootour.api.data.ApiResponse
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class CallAdapterFactory : CallAdapter.Factory() {

    override fun get(returnType: Type, annotations: Array<Annotation>, retrofit: Retrofit) =
        when (getRawType(returnType)) {
            Call::class.java -> {
                val callType = getParameterUpperBound(0, returnType as ParameterizedType)
                when (getRawType(callType)) {
                    ApiResponse::class.java -> {
                        val resultType = getParameterUpperBound(0, callType as ParameterizedType)
                        ResponseAdapter(resultType)
                    }
                    else -> null
                }
            }
            else -> null
        }
}