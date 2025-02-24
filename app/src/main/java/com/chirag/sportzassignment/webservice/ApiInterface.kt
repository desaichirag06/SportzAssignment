package com.chirag.sportzassignment.webservice

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Url

/**
 * Created by Chirag Desai
 */
interface ApiInterface {
    @GET
    fun callGetMethod(
        @Url url: String?
    ): Call<ResponseBody?>?

    @POST
    fun callPostMethod(
        @Url url: String?,
        @Body body: HashMap<String, Any>?
    ): Call<ResponseBody?>?


}