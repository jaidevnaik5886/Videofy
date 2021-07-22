package com.jaidev.videofy.network.data

import com.google.gson.Gson
import com.jaidev.videofy.response.VideoData
import com.jaidev.videofy.response.VideoDataResponse
import com.jaidev.videofy.utils.Constants
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject

@Module
@InstallIn(SingletonComponent::class)
class DataRepository @Inject constructor() {

     fun getVideoList(): List<VideoData> {
        return Gson().fromJson(Constants.SAMPLE_JSON, VideoDataResponse::class.java)
            .extractVideoList()
    }

}

private fun VideoDataResponse.extractVideoList(): List<VideoData> {
    val videoList = mutableListOf<VideoData>()
    for (category in categories) {
        for (videoData in category.videoData) {
            videoList.add(videoData)
        }
    }
    return videoList
}
