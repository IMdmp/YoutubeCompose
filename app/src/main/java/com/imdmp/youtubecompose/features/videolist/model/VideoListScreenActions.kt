package com.imdmp.youtubecompose.features.videolist.model

import com.imdmp.youtubecompose.features.videolist.topappbar.ToolbarActions
import com.imdmp.youtubecompose.features.videolist.videoitem.VideoItemActions

interface VideoListScreenActions : VideoItemActions, ToolbarActions {
    companion object {
        fun default(): VideoListScreenActions = object : VideoListScreenActions {
            override fun videoItemSelected(videoListItem: VideoListItem) {
                TODO("Not yet implemented")
            }

            override fun searchClicked() {
                TODO("Not yet implemented")
            }

            override fun profileClicked() {
                TODO("Not yet implemented")
            }

        }
    }
}