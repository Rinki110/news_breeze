package com.example.newsbreezer.data.network

sealed class ResultData <out T > {

    data class Loading<out T>(val value: T? = null) : ResultData<T>()
    data class Success<out T>(val value: T) : ResultData<T>()
    data class Failure<out T>(val message : String) : ResultData<T>()

}





