package com.chirag.sportzassignment.utils

import android.content.Context
import android.net.ConnectivityManager

/**
 * Created by Chirag Desai on 22-02-2025.
 */
object Utilities {

    @JvmStatic
    fun isConnectingToInternet(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}