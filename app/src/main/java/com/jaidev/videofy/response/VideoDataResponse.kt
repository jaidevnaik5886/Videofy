package com.jaidev.videofy.response


import com.google.gson.annotations.SerializedName

data class VideoDataResponse(
    @SerializedName("categories")
    val categories: List<Category>
)