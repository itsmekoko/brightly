package com.kokodeco.brightlyapp.util

import android.content.Context
import android.net.ConnectivityManager
import javax.inject.Inject

class NetworkConnectivityChecker @Inject constructor(private val context: Context) {
    fun hasNetworkAccess(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetworkInfo
        return activeNetwork?.isConnectedOrConnecting == true
    }
}