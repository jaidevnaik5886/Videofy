package com.jaidev.videofy.ui.home

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.jaidev.videofy.R
import com.jaidev.videofy.base.BaseFragment
import com.jaidev.videofy.base.BaseViewModel
import com.jaidev.videofy.common.ListItem
import com.jaidev.videofy.common.RecyclerViewCallback
import com.jaidev.videofy.common.addOrUpdateDataSource
import com.jaidev.videofy.databinding.HomeFragmentBinding
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
            binding.rvVideoList.addOrUpdateDataSource(
                it ?: emptyList(),
                R.layout.list_item_video,
                object : RecyclerViewCallback {
                    override fun onClick(item: ListItem) {
                        model.onVideoItemSelected(item as VideoData)
                    }
                },
                R.string.no_data_available
            )
        })
    }

    override fun handleEvent(event: BaseEvent) {
        when (event) {
            is NavToDetails -> {
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToDetailFragment()
                )
            }
        }
    }
}