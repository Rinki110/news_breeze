package com.example.newsbreezer.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.os.Build
import com.example.newsbreezer.utils.NoInternetException
import okhttp3.Interceptor
import okhttp3.Response


class NetworkConnectionInterceptor(
    context: Context
) : Interceptor {

    private val applicationContext = context.applicationContext

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isInternetAvailable())
            throw NoInternetException("Make sure you have an active data connection")
        return chain.proceed(chain.request())
    }

    private fun isInternetAvailable(): Boolean {
        var result = false
        val connectivityManager = applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        connectivityManager?.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                it.getNetworkCapabilities(connectivityManager.activeNetwork)?.apply {
                    result = when {
                        hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                        hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                        else -> false
                    }
                }
            } else {
                val allNetworks: Array<Network> = it.allNetworks // added in API 21 (Lollipop)
                for (network in allNetworks) {
                    val networkCapabilities: NetworkCapabilities? = it.getNetworkCapabilities(network)
                    if (networkCapabilities != null) {
                        if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                                || networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                                || networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) result = true
                    }
                }
            }
        }
        return result
    }

}




