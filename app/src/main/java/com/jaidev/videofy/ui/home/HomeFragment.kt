package com.jaidev.videofy.ui.home

import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.google.android.exoplayer2.Player
import com.jaidev.videofy.R
import com.jaidev.videofy.base.BaseFragment
import com.jaidev.videofy.base.BaseViewModel
import com.jaidev.videofy.bindings.VideoPlayBackCallback
import com.jaidev.videofy.common.ListItem
import com.jaidev.videofy.common.addDataSource
import com.jaidev.videofy.databinding.HomeFragmentBinding
import com.jaidev.videofy.databinding.ListItemVideoBinding
import com.jaidev.videofy.response.VideoData
import com.jaidev.videofy.utils.BaseEvent
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeFragmentBinding>(R.layout.home_fragment, R.string.home) {

    val model: HomeViewModel by viewModels()

    override fun getVM(): BaseViewModel = model

    override fun attachBinding() {
        binding.handler = this
        binding.vm = model
        model.video.observe(viewLifecycleOwner, {
            binding.rvVideoList.addDataSource(
                it ?: emptyList(),
                R.layout.list_item_video,
                object : VideoPlayBackCallback {

                    override fun onItemSelectedClick(item: ListItem, position: Int) {
                        model.onVideoItemSelected(this, item as VideoData, position)
                    }

                    override fun onVideoDurationRetrieved(duration: Long, player: Player) {

                    }

                    override fun onVideoBuffering(player: Player) {

                    }

                    override fun onStartedPlaying(player: Player) {

                    }

                    override fun onFinishedPlaying(player: Player) {

                    }
                },
                R.string.no_data_available
            )
        })
    }

    override fun handleEvent(event: BaseEvent) {
        super.handleEvent(event)
        when (event) {
            is NavToDetails -> {
                navigate(event.videoData, event.callback, event.position)
            }
        }
    }

    private fun navigate(videoData: VideoData, callback: VideoPlayBackCallback, position: Int) {
        val videoBinding = ListItemVideoBinding.inflate(LayoutInflater.from(context))
        videoBinding.model = videoData
        videoBinding.callback = callback
        videoBinding.position = position
        videoBinding.executePendingBindings()
        val extras = FragmentNavigatorExtras(
            videoBinding.itemVideoExoplayer to videoBinding.itemVideoExoplayer.transitionName,
            videoBinding.txtTitle to videoBinding.txtTitle.transitionName,
            videoBinding.txtSubTitle to videoBinding.txtSubTitle.transitionName,
        )
        findNavController().navigate(
            HomeFragmentDirections.actionHomeFragmentToDetailFragment(videoData, position), extras
        )
    }

}