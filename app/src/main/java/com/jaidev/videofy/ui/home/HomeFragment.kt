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
import com.jaidev.videofy.common.addOrUpdateDataSource
import com.jaidev.videofy.databinding.HomeFragmentBinding
import com.jaidev.videofy.databinding.ListItemVideoBinding
import com.jaidev.videofy.response.VideoData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeFragmentBinding>(R.layout.home_fragment, R.string.home) {

    val model: HomeViewModel by viewModels()

    override fun getVM(): BaseViewModel = model

    override fun attachBinding() {
        binding.handler = this
        binding.vm = model
        postponeEnterTransition()
        model.video.observe(viewLifecycleOwner, {
            binding.rvVideoList.addOrUpdateDataSource(
                it ?: emptyList(),
                R.layout.list_item_video,
                object : VideoPlayBackCallback {

                    override fun onItemSelectedClick(item: ListItem) {
                        navigate(item as VideoData,this)
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

    private fun navigate(videoData: VideoData, callback: VideoPlayBackCallback) {
        val binding = ListItemVideoBinding.inflate(LayoutInflater.from(context))
        binding.model = videoData
        binding.callback = callback
        binding.executePendingBindings()
        val extras = FragmentNavigatorExtras(
            binding.itemVideoExoplayer to binding.itemVideoExoplayer.transitionName,
        )
        findNavController().navigate(
            HomeFragmentDirections.actionHomeFragmentToDetailFragment(videoData), extras
        )
    }

}