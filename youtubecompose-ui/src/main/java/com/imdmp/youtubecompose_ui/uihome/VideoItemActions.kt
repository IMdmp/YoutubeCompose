package com.imdmp.youtubecompose_ui.uihome

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