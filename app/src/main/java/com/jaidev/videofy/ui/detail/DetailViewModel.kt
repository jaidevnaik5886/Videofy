package com.jaidev.videofy.ui.detail

import com.jaidev.videofy.base.BaseViewModel
import com.jaidev.videofy.network.data.DataRepository
import com.jaidev.videofy.utils.NetworkUtilities
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    val networkUtilities: NetworkUtilities
) :
    BaseViewModel() {



    init {

    }


}

