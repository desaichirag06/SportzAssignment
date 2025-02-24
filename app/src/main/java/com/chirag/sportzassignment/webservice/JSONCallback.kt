package com.chirag.sportzassignment.webservice

import android.content.Context
import com.chirag.sportzassignment.R
import com.chirag.sportzassignment.utils.CustomProgressDialog
import com.chirag.sportzassignment.utils.Logger
import com.chirag.sportzassignment.utils.Utilities.isConnectingToInternet
import okhttp3.ResponseBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

abstract class JSONCallback @JvmOverloads constructor(
    private val context: Context,
    dialog: CustomProgressDialog? = null
) :
    Callback<ResponseBody?> {
    init {
        dialog?.show()

        if (!isConnectingToInternet(context)) {
            throw Exception(context.getString(R.string.no_internet_connection))
        }
    }

    override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
        var body: String? = null
        try { //Converting string to JSONObject
            if (response.isSuccessful) {
                if (response.body() != null) {
                    body = (response.body() as ResponseBody).string()
                }
                if (body != null) {

                    val `object` = JSONObject(body)
                    Logger.e(
                        "Response---->",
                        """
                                ${call.request().url}
                                $body
                                """.trimIndent()
                    )
                    if (`object`.has("Matchdetail")) {
                        onSuccess(response.code(), `object`)
                    } else {
                        onFailure(response.code(), `object`)
                    }

                }
            } else {
                if (response.errorBody() != null) {
                    body = response.errorBody()!!.string()
                }
                if (body != null) {
                    if (body.isEmpty()) {
                        val message = response.raw().message
                        Logger.e(
                            "Response",
                            """
                                ${call.request().url}
                                $message
                                """.trimIndent()
                        )
                        onFailed(response.code(), message)
                    } else {
                        val `object` = JSONObject(body)
                        Logger.e(
                            "Response",
                            """
                                ${call.request().url}
                                $`object`
                                """.trimIndent()
                        )
                        onFailure(response.code(), `object`)
                    }
                }
            }
        } catch (e: JSONException) {
            e.printStackTrace()
            if (body != null) Logger.e(body)

            //            Utils.generateCrashReport(context, call, body);
            //dialog.dismiss();
            //commonFunctions.progressHide();
            if (body != null && (body.contains("PAN") || body.contains("true"))) {
                try {
                    val builder = "{data:$body}"
                    onSuccess(response.code(), JSONObject(builder))
                    return
                } catch (ex: JSONException) {
                    e.printStackTrace()
                }
            }
            checkNotNull(body)
            if (body.startsWith("\"")) {
                onFailed(response.code(), body)
            } else {
                onFailed(response.code(), context.getString(R.string.something_went_wrong))
            }
        } catch (e: IOException) {
            e.printStackTrace()
            if (body != null) Logger.e(body)

            if (body != null && (body.contains("PAN") || body.contains("true"))) {
                try {
                    val builder = "{data:$body}"
                    onSuccess(response.code(), JSONObject(builder))
                    return
                } catch (ex: JSONException) {
                    e.printStackTrace()
                }
            }
            checkNotNull(body)
            if (body.startsWith("\"")) {
                onFailed(response.code(), body)
            } else {
                onFailed(response.code(), context.getString(R.string.something_went_wrong))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            onFailed(response.code(), context.getString(R.string.something_went_wrong))
            //Log.e("e", e.getMessage());
        }
    }

    override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
        Logger.e(
            "Response", """
     ${call.request().url}
     $t
     """.trimIndent()
        )
        if (!isConnectingToInternet(context)) {
            onFailed(0, context.getString(R.string.no_internet_connection))
        } else if (t is ConnectException
            || t is SocketTimeoutException
            || t is UnknownHostException
        ) {
            onFailed(0, context.getString(R.string.failed_to_connect_with_server))
        } else if (t is IOException) {
            onFailed(0, context.getString(R.string.no_internet_connection))
        } else {
            onFailed(0, t.message)
        }
    }

    private fun onFailure(statusCode: Int, `object`: JSONObject) {
        if (`object`.optJSONArray("valid_members") != null) {
            onFailed(statusCode, `object`.toString())
        } else {
            onFailed(statusCode, `object`.optString("message"))
        }
    }

    protected abstract fun onFailed(statusCode: Int, message: String?)

    @Throws(JSONException::class)
    protected abstract fun onSuccess(statusCode: Int, jsonObject: JSONObject?)
}
