package com.example.newsbreezer.model

import com.google.gson.annotations.SerializedName


data class PostsModel(
        @SerializedName("body")
        var body: String?,
        @SerializedName("id")
        var id: Int?,
        @SerializedName("title")
        var title: String?,
        @SerializedName("userId")
        var userId: Int?
)


