package com.jaidev.videofy.response


import com.google.gson.annotations.SerializedName

data class Category(
    @SerializedName("name")
    val name: String,
    @SerializedName("videos")
    val videoData: List<VideoData>
)