package com.imdmp.youtubecompose.features.videolist

interface VideoListScreenActions: VideoItemActions, ToolbarActions {
    companion object{
        fun default() :VideoListScreenActions = object:VideoListScreenActions{
            override fun videoItemSelected(videoListItem: VideoListItem) {
                TODO("Not yet implemented")
            }

            override fun searchClicked() {
                TODO("Not yet implemented")
            }

        }
    }
}