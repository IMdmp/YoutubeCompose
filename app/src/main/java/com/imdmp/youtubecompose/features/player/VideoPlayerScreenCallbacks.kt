package com.imdmp.youtubecompose.features.player

import com.google.android.exoplayer2.source.MediaSource

interface VideoPlayerScreenCallbacks {

    suspend fun getMediaSource(url:String):MediaSource

    fun prepareAndPlayVideoPlayer(url:String)

    fun disposeVideoPlayer()

    fun selectFullScreen()

    companion object{
        fun default():VideoPlayerScreenCallbacks = object: VideoPlayerScreenCallbacks{
            override suspend fun getMediaSource(url: String): MediaSource {
                TODO("Not yet implemented")
            }

            override fun prepareAndPlayVideoPlayer(url: String) {
                TODO("Not yet implemented")
            }

            override fun disposeVideoPlayer() {
                TODO("Not yet implemented")
            }

            override fun selectFullScreen() {
                TODO("Not yet implemented")
            }

        }
    }
}