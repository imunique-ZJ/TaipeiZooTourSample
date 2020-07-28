package zj.app.taipeizootour.api

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import zj.app.taipeizootour.api.data.ApiResponse
import java.io.IOException
import java.net.HttpURLConnection.HTTP_MULT_CHOICE
import java.net.HttpURLConnection.HTTP_OK

class ResponseCall<T>(proxy: Call<T>) : CallDelegate<T, ApiResponse<T>>(proxy) {

    override fun executeImpl(): Response<ApiResponse<T>> {
        return try {
            val response = proxy.execute()
            val result = wrapResponse(response)
            Response.success(result)
        } catch (t: Throwable) {
            val result = wrapFailure(proxy, t)
            Response.success(result)
        }
    }

    override fun enqueueImpl(callback: Callback<ApiResponse<T>>) = proxy.enqueue(object: Callback<T> {
        override fun onResponse(call: Call<T>, apiResponse: Response<T>) {
            val result = wrapResponse(apiResponse)
            callback.onResponse(this@ResponseCall, Response.success(result))
        }

        override fun onFailure(call: Call<T>, t: Throwable) {
            val result = wrapFailure(call, t)
            callback.onResponse(this@ResponseCall, Response.success(result))
        }
    })

    override fun cloneImpl() = ResponseCall(proxy.clone())

    private fun wrapResponse(apiResponse: Response<T>): ApiResponse<T> {
        val httpStatusCode = apiResponse.code()
        val url = apiResponse.raw().request().url().toString()
        val body = apiResponse.body()

        return when(httpStatusCode) {
            in HTTP_OK until HTTP_MULT_CHOICE -> ApiResponse.Success(url, httpStatusCode, body)
            else -> ApiResponse.Failure(url, httpStatusCode, null)
        }
    }

    private fun wrapFailure(call: Call<T>, t: Throwable): ApiResponse<T> {
        val url = call.request().url().toString()
        return when(t) {
            is IOException -> ApiResponse.NetworkError
            else -> ApiResponse.Failure(url, null, t)
        }
    }
}