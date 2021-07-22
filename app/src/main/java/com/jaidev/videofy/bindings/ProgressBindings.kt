package com.jaidev.videofy.bindings

import android.view.View
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData

@BindingAdapter(value = ["isBusy", "lifecycle"], requireAll = false)
fun ViewGroup.setProgress(isBusy: LiveData<Boolean>, lifecycleOwner: LifecycleOwner) {
    isBusy.observe(lifecycleOwner, {
        if (it) {
            visibility = View.VISIBLE
        } else {
            visibility = View.GONE
        }
    })
}
