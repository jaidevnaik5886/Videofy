package com.jaidev.videofy.ui.detail

import androidx.core.view.ViewCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.google.android.exoplayer2.Player
import com.jaidev.videofy.R
import com.jaidev.videofy.base.BaseFragment
import com.jaidev.videofy.base.BaseViewModel
import com.jaidev.videofy.bindings.VideoPlayBackCallback
import com.jaidev.videofy.common.ListItem
import com.jaidev.videofy.databinding.DetailFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : BaseFragment<DetailFragmentBinding>(R.layout.detail_fragment),
    VideoPlayBackCallback {

    val model: DetailViewModel by viewModels()
    val args by navArgs<DetailFragmentArgs>()

    override fun getVM(): BaseViewModel = model

    override fun attachBinding() {
        binding.handler = this
        binding.callback = this
        binding.vm = model
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        ViewCompat.setTransitionName(binding.itemVideoExoplayer, args.videoData.toString())
    }

    override fun onItemSelectedClick(item: ListItem) {

    }

    override fun onVideoDurationRetrieved(duration: Long, player: Player) {

    }

    override fun onVideoBuffering(player: Player) {

    }

    override fun onStartedPlaying(player: Player) {

    }

    override fun onFinishedPlaying(player: Player) {

    }
}