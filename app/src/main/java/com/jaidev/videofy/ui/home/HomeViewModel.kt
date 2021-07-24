package com.jaidev.videofy.ui.home

import androidx.lifecycle.MutableLiveData
import com.jaidev.videofy.base.BaseViewModel
import com.jaidev.videofy.bindings.VideoPlayBackCallback
import com.jaidev.videofy.network.data.DataRepository
import com.jaidev.videofy.response.VideoData
import com.jaidev.videofy.utils.BaseEvent
import com.jaidev.videofy.utils.NetworkUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val detailRepository: DataRepository,
    val networkUtils: NetworkUtils
) :
    BaseViewModel() {

    val video = MutableLiveData<List<VideoData>>()

    init {
        if (networkUtils.isConnected()) {
            fetchVideos()
        }
    }

    private fun fetchVideos() {
        launch {
            val list = detailRepository.getVideoList()
            video.postValue(list)
        }
    }

    fun onVideoItemSelected(callback: VideoPlayBackCallback, videoData: VideoData, position: Int) {
        sendEvent(NavToDetails(callback, videoData, position))
    }

}

class NavToDetails(var callback: VideoPlayBackCallback, var videoData: VideoData, var position: Int) : BaseEvent()
