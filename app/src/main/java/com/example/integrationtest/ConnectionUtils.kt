package com.example.integrationtest

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.telephony.TelephonyManager

internal object ConnectionUtils {

    fun getConnectionType(context: Context): String {
        return try {
            val connectivityManager =
                    context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkCapabilities = connectivityManager.activeNetwork ?: return "na1"
            val activeNetwork =
                    connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return "na2"
            val result = when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> "wifi"
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> "cellular"
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> "ethernet"
                else -> "unknown"
            }
            return result
        } catch (t: Throwable) {
            ""
        }
    }

    internal fun getMobileCarrierName(context: Context): String {
        return try {
            val telephonyManager =
                    context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager?
            telephonyManager?.networkOperatorName ?: ""
        } catch (t: Throwable) {
            ""
        }
    }

    internal fun isOnline(context: Context): Boolean {
        return try {
            val connectivityManager =
                    context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val capabilities =
                    connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
        } catch (t: Throwable) {
            false
        }
    }
}
