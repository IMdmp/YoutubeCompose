package com.imdmp.ui_home

interface VideoItemActions {
    fun videoItemSelected(videoListItem: com.imdmp.ui_home.VideoListItem)

    companion object {
        fun default(): VideoItemActions = object : VideoItemActions {
            override fun videoItemSelected(videoListItem: com.imdmp.ui_home.VideoListItem) {
                TODO("Not yet implemented")
            }

        }
    }
}