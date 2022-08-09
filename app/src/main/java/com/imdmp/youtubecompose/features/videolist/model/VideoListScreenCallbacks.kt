package com.imdmp.youtubecompose.features.videolist.model

import com.imdmp.youtubecompose.features.videolist.search.SearchScreenCallbacks
import com.imdmp.youtubecompose.features.videolist.topappbar.ToolbarActions

interface VideoListScreenCallbacks : VideoItemActions, ToolbarActions, SearchScreenCallbacks {
    companion object {
        fun default(): VideoListScreenCallbacks = object : VideoListScreenCallbacks {
            override fun videoItemSelected(videoListItem: VideoListItem) {
                TODO("Not yet implemented")
            }

            override fun searchClicked() {
                TODO("Not yet implemented")
            }

            override fun profileClicked() {
                TODO("Not yet implemented")
            }

            override fun onSearchClicked(query: String) {
                TODO("Not yet implemented")
            }

            override fun textBoxClicked() {
                TODO("Not yet implemented")
            }

            override fun textBoxCancelClicked() {
                TODO("Not yet implemented")
            }

            override fun onSearchTextValueChanged(newValue: String) {
                TODO("Not yet implemented")
            }

            override fun onBackButtonClicked() {
                TODO("Not yet implemented")
            }

            override fun suggestionSelected() {
                TODO("Not yet implemented")
            }

        }
    }
}