package com.imdmp.uihome

interface VideoItemActions {
    fun videoItemSelected(videoListItem: com.imdmp.uihome.VideoListItem)

    companion object {
        fun default(): VideoItemActions = object : VideoItemActions {
            override fun videoItemSelected(videoListItem: com.imdmp.uihome.VideoListItem) {
                TODO("Not yet implemented")
            }

        }
    }
}