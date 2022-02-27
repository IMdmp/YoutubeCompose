package com.imdmp.youtubecompose.features.videoplayer.playback

interface PlaybackScreenCallbacks {
    fun prepareAndPlayVideoPlayer(url: String = "")

    fun disposeVideoPlayer()

    companion object {
        fun default(): PlaybackScreenCallbacks = object : PlaybackScreenCallbacks {
            override fun prepareAndPlayVideoPlayer(url: String) {
                TODO("Not yet implemented")
            }

            override fun disposeVideoPlayer() {
                TODO("Not yet implemented")
            }

        }
    }
}