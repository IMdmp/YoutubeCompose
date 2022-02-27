package com.imdmp.youtubecompose.features.videoplayer.model

import com.google.android.exoplayer2.source.MediaSource
import com.imdmp.youtubecompose.features.videoplayer.controls.ControlsCallback
import com.imdmp.youtubecompose.features.videoplayer.playback.PlaybackScreenCallbacks

interface VideoPlayerScreenCallbacks : PlaybackScreenCallbacks, ControlsCallback {

    suspend fun getMediaSource(url: String): MediaSource

    fun retrieveComments() {
    }

    companion object {
        fun default(): VideoPlayerScreenCallbacks = object : VideoPlayerScreenCallbacks {
            override suspend fun getMediaSource(url: String): MediaSource {
                TODO("Not yet implemented")
            }

            override fun prepareAndPlayVideoPlayer(url: String) {
//                TODO("Not yet implemented")
            }

            override fun disposeVideoPlayer() {
//                TODO("Not yet implemented")
            }

            override fun fullScreenClicked() {
                TODO("Not yet implemented")
            }

            override fun pauseOrPlayClicked() {
                TODO("Not yet implemented")
            }

        }
    }
}