package com.jaidev.videofy.utils

import android.content.Context
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@Module
@InstallIn(ViewModelComponent::class)
class NetworkUtilities @Inject constructor(@ApplicationContext val applicationContext: Context) :
    NetworkConnectivity {

    override fun isConnected(): Boolean {
        return Utilities.isInternetAvailable(applicationContext)
    }
}

interface NetworkConnectivity {
    fun isConnected(): Boolean
}