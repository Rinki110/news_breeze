package com.example.newsbreezer.data.network

import android.util.Log

import com.example.newsbreezer.model.BreakingNewsModel
import okhttp3.*
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit


//define all api
interface ApiService {


    @GET(APIRoutes.TOP_HEADLINES)
    suspend fun getTopHeadLines(
        @Query("q") q: String = "Apple"
       // @Query("storeId") storeId: Int? = null
    ): Response<BreakingNewsModel>


    companion object {

        operator fun invoke(
            networkConnectionInterceptor: NetworkConnectionInterceptor,
        ): ApiService {

            val okkHttpclient = OkHttpClient().newBuilder()
                .connectTimeout(90, TimeUnit.SECONDS)
                .readTimeout(90, TimeUnit.SECONDS)
                .writeTimeout(90, TimeUnit.SECONDS)
                .addInterceptor(networkConnectionInterceptor)
                .addInterceptor { chain ->

                    val requestBuilder: Request.Builder = chain.request().newBuilder()
                    requestBuilder.header("Content-Type", "application/json")
                    requestBuilder.header("x-api-key", "eacaae5100144591a312953df5a37f5b")

                    val request: Request = requestBuilder.build()

                    Log.e("ApiService", "url :  ${chain.request().url()}  \n  RequestMethod :  ${request.method()}  \n  " +
                            "Headers :  ${request.headers()} ")
                    chain.proceed(request)


                }
                .build()

            return Retrofit.Builder()
                .client(okkHttpclient)
                .baseUrl(APIRoutes.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }

    }

}






