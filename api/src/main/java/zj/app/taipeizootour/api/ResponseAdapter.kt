package zj.app.taipeizootour.api

import retrofit2.Call
import retrofit2.CallAdapter
import zj.app.taipeizootour.api.data.ApiResponse
import java.lang.reflect.Type

class ResponseAdapter(private val type: Type) : CallAdapter<Type, Call<ApiResponse<Type>>> {
    override fun responseType() = type
    override fun adapt(call: Call<Type>): Call<ApiResponse<Type>> = ResponseCall(call)
}