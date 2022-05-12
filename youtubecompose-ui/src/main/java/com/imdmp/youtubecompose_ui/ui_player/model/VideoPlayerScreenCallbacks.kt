package com.imdmp.youtubecompose_ui.ui_player.model

import com.imdmp.youtubecompose_ui.ui_player.controls.ControlsCallback
import com.imdmp.youtubecompose_ui.ui_player.playback.PlaybackScreenCallbacks

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
