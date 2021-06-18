package com.example.newsbreezer.data.repository

import com.example.newsbreezer.data.network.ApiService
import com.example.newsbreezer.data.network.SafeApiRequest
import com.example.newsbreezer.model.PostsModel
import com.example.newsbreezer.model.BreakingNewsModel



class MainRepository (
    private val api: ApiService,

) : SafeApiRequest() {

    suspend fun getTopHeadLines() : BreakingNewsModel {
        return apiRequest { api.getTopHeadLines() }
    }



}