package com.chirag.sportzassignment.webservice

import com.chirag.sportzassignment.misc.Constants.Companion.BASE_URL
import com.chirag.sportzassignment.utils.Logger
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class CustomRetrofit {
    private var baseURL: String? = null
    private var endPoint: String? = null
    private var endPointExtra = ""

    fun setAPI(endPoint: String): CustomRetrofit {
        this.endPoint = endPoint
        Logger.e("URL", BASE_URL + endPoint)
        return this
    }

    fun setUrl(baseUrl: String?): CustomRetrofit {
        baseURL = baseUrl
        Logger.e("baseUrl", baseUrl!!)
        return this
    }


    fun setGetParameters(params: HashMap<String, Any>?): CustomRetrofit {
        if (!params.isNullOrEmpty()) {
            for ((key, value) in params) {
                Logger.e("params", key + "\t" + value)
                endPointExtra = if (endPointExtra.contains("?")) {
                    "$endPointExtra&$key=$value"
                } else {
                    "$endPointExtra?$key=$value"
                }
            }
            Logger.e("EndpointExtra: ", endPointExtra)
        }
        return this
    }

    fun setCallBackListener(listener: JSONCallback?) {
        if (listener != null) {
            makeCall().enqueue(listener)
        }
    }

    private fun makeCall(): Call<ResponseBody?> {
        val call: Call<ResponseBody?>
        val client: OkHttpClient = OkHttpClient.Builder().connectTimeout(80, TimeUnit.SECONDS)
            .readTimeout(140, TimeUnit.SECONDS).writeTimeout(80, TimeUnit.SECONDS)
            .addInterceptor(Interceptor { chain: Interceptor.Chain ->
                val original = chain.request()
                val request =
                    chain.request().newBuilder().method(original.method, original.body).build()
                chain.proceed(request)
            }).build()
        val apiInterface = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client).addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(ApiInterface::class.java)
        Logger.e("baseUrl1", baseURL + endPoint)

        call =
            apiInterface.callGetMethod(
                url = BASE_URL+ endPoint
            )!!
        return call
    }

    companion object {
        fun with(): CustomRetrofit {
            return CustomRetrofit()
        }
    }
}