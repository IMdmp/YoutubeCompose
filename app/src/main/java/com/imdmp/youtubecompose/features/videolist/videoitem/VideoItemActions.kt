package com.imdmp.youtubecompose.features.videolist.videoitem

import com.imdmp.youtubecompose.features.videolist.model.VideoListItem

interface VideoItemActions {
    fun videoItemSelected(videoListItem: VideoListItem)

    companion object {
        fun default(): VideoItemActions = object : VideoItemActions {
            override fun videoItemSelected(videoListItem: VideoListItem) {
                TODO("Not yet implemented")
            }

        }
    }
}