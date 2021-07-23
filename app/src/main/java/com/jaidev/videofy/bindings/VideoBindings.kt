package com.jaidev.videofy.bindings

import android.net.Uri
import androidx.databinding.BindingAdapter
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.TrackGroupArray
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelectionArray
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.jaidev.videofy.common.ListItem
import com.jaidev.videofy.common.RecyclerViewCallback

@BindingAdapter("video_url", "on_state_change")
fun PlayerView.loadVideo(url: String?, stateCallback: VideoPlayBackCallback) {
    if (url == null) return
    val player = ExoPlayerFactory.newSimpleInstance(
        DefaultRenderersFactory(context), DefaultTrackSelector(),
        DefaultLoadControl()
    )
    player.playWhenReady = true
    player.volume = 0f
    player.repeatMode = Player.REPEAT_MODE_ALL
    setKeepContentOnPlayerReset(true)
    this.useController = false
    val mediaSource = ExtractorMediaSource.Factory(
        DefaultHttpDataSourceFactory("Demo")
    ).createMediaSource(Uri.parse(url))

    player.prepare(mediaSource)
    this.player = player
    this.player!!.addListener(object : Player.EventListener {
        override fun onTimelineChanged(timeline: Timeline?, manifest: Any?, reason: Int) {

        }

        override fun onTracksChanged(
            trackGroups: TrackGroupArray?,
            trackSelections: TrackSelectionArray?
        ) {
        }

        override fun onLoadingChanged(isLoading: Boolean) {
        }

        override fun onRepeatModeChanged(repeatMode: Int) {
        }

        override fun onShuffleModeEnabledChanged(shuffleModeEnabled: Boolean) {
        }

        override fun onPositionDiscontinuity(reason: Int) {
        }

        override fun onPlaybackParametersChanged(playbackParameters: PlaybackParameters?) {
        }

        override fun onSeekProcessed() {
        }

        override fun onPlayerError(error: ExoPlaybackException?) {

        }

        override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
            if (playbackState == Player.STATE_BUFFERING) stateCallback.onVideoBuffering(player) // Buffering.. set progress bar visible here
            if (playbackState == Player.STATE_READY) {
                // [PlayerView] has fetched the video duration so this is the block to hide the buffering progress bar
                stateCallback.onVideoDurationRetrieved(this@loadVideo.player.duration, player)
            }
            if (playbackState == Player.STATE_READY && player.playWhenReady) {
                // [PlayerView] has started playing/resumed the video
                stateCallback.onStartedPlaying(player)
            }
        }
    })
}



interface VideoPlayBackCallback : RecyclerViewCallback{

    fun onItemSelectedClick(item: ListItem)

    fun onVideoDurationRetrieved(duration: Long, player: Player)

    fun onVideoBuffering(player: Player)

    fun onStartedPlaying(player: Player)

    fun onFinishedPlaying(player: Player)
}
