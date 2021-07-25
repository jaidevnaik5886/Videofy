package com.jaidev.videofy.ui.home

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import com.jaidev.videofy.network.data.DataRepository
import com.jaidev.videofy.utils.NetworkUtils
import junit.framework.TestCase
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Config(manifest = "src/main/AndroidManifest.xml")
@RunWith(RobolectricTestRunner::class)
class HomeViewModelTest : TestCase() {

    private lateinit var viewModel: HomeViewModel

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    val context = ApplicationProvider.getApplicationContext<Context>()
    val dataRepository = DataRepository()
    val networkUtils = NetworkUtils(context)

    @Before
    public override fun setUp() {
        viewModel = HomeViewModel(dataRepository, networkUtils)
    }


    @Test
    fun `check if internet is available before fetchVideos`() {
        val isInternet = networkUtils.isConnected()
        Assert.assertEquals(true, isInternet)
    }
}