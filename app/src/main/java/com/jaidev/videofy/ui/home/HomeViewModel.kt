package com.jaidev.videofy.ui.home

import androidx.lifecycle.MutableLiveData
import com.jaidev.videofy.base.BaseViewModel
import com.jaidev.videofy.network.data.DataRepository
import com.jaidev.videofy.response.VideoData
import com.jaidev.videofy.utils.BaseEvent
import com.jaidev.videofy.utils.NetworkUtilities
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val detailRepository: DataRepository,
    val networkUtilities: NetworkUtilities
) :
    BaseViewModel() {

    val video = MutableLiveData<List<VideoData>>()

    init {
        if (networkUtilities.isConnected()) {
            fetchVideos()
        }
    }

    private fun fetchVideos() {
        launch {
            val list = detailRepository.getVideoList()
            video.postValue(list)
        }
    }

    fun onVideoItemSelected(videoData: VideoData) {
        sendEvent(NavToDetails())
    }

}

class NavToDetails() : BaseEvent()