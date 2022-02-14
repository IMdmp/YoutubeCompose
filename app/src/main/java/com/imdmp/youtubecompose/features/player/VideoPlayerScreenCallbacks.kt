package com.imdmp.youtubecompose.features.player

import com.google.android.exoplayer2.source.MediaSource

interface VideoPlayerScreenCallbacks {

    suspend fun getMediaSource(url:String):MediaSource

    companion object{
        fun default():VideoPlayerScreenCallbacks = object: VideoPlayerScreenCallbacks{
            override suspend fun getMediaSource(url: String): MediaSource {
                TODO("Not yet implemented")
            }

        }
    }
}