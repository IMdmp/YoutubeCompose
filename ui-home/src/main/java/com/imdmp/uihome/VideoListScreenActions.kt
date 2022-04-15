package com.imdmp.uihome

import com.imdmp.uihome.topappbar.ToolbarActions

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