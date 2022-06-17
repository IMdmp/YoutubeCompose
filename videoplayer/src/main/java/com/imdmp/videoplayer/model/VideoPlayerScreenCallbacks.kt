package com.imdmp.videoplayer.model

import com.imdmp.videoplayer.controls.ControlsCallback
import com.imdmp.videoplayer.playback.PlaybackScreenCallbacks

interface VideoPlayerScreenCallbacks : PlaybackScreenCallbacks, ControlsCallback {

    fun retrieveComments() {
    }

    companion object {
        fun default(): VideoPlayerScreenCallbacks = object : VideoPlayerScreenCallbacks {
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
