package com.example.newsbreezer.data.network

import android.util.Log
import com.example.newsbreezer.utils.ApiException
import com.example.newsbreezer.utils.AppConstants
import com.google.gson.Gson
import org.json.JSONObject
import retrofit2.Response
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLHandshakeException
import javax.net.ssl.SSLKeyException
import javax.net.ssl.SSLPeerUnverifiedException


abstract class SafeApiRequest {

    private val TAG = "SafeApiRequest"

    suspend fun <T : Any> apiRequest(call: suspend () -> Response<T>): T = try {
        val response = call.invoke()
        if (response.isSuccessful) {
            response.body()?.let {
                return it
            }
            throw ApiException(AppConstants.API_ERROR_DEFAULT_MSG)
        }
        else {
            //handle error and errorCode
            try {
                val jsonError = JSONObject(Gson().toJson(response.errorBody().toString()))
                Log.e(TAG, "Error response :----------error code= ${response.code()} \n ErrorJson=  $jsonError ")
                throw ApiException("Error Code: ${response.code()} ${jsonError.getString(AppConstants.API_RESULT_ERROR_MSG)}")
            } catch (e: Exception) {
                e.printStackTrace()
                throw ApiException("Error Code: ${response.code()} ${AppConstants.API_ERROR_DEFAULT_MSG}")
            }
        }
    } catch (e: SocketTimeoutException) {
        e.printStackTrace()
        throw ApiException("Something went wrong.Please try Again.")
    } catch (e: SocketException) {
        e.printStackTrace()
        throw ApiException("Something went wrong.Please try Again.")
    } catch (e: SSLKeyException) {
        e.printStackTrace()
        throw ApiException("Invalid SSL certificate")
    } catch (e: SSLPeerUnverifiedException) {
        e.printStackTrace()
        throw ApiException("Certificate pinning failure!")
    } catch (e: SSLHandshakeException) {
        e.printStackTrace()
        throw ApiException("Trust anchor for certification path not found.")
    } catch (e: UnknownHostException) {
        e.printStackTrace()
        throw ApiException("Unable to resolve host ${APIRoutes.BASE_URL}: No address associated with hostname.")
    }
}





