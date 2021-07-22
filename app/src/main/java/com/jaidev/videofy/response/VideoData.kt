package com.jaidev.videofy.response


import com.google.gson.annotations.SerializedName
import com.jaidev.videofy.common.ListItem
import java.io.Serializable

data class VideoData(
    @SerializedName("description")
    val description: String,
    @SerializedName("sources")
    val sources: List<String>,
    @SerializedName("subtitle")
    val subtitle: String,
    @SerializedName("thumb")
    val thumb: String,
    @SerializedName("title")
    val title: String
): ListItem, Serializable