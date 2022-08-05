package com.imdmp.youtubecompose.features.videolist.events

import com.imdmp.youtubecompose.features.videolist.ViewModelEvent
import com.imdmp.youtubecompose.features.videolist.model.VideoListItem

sealed class VideoListEvents : ViewModelEvent() {
    class BackPressedEvent : VideoListEvents()

    class SearchIconClickedEvent : VideoListEvents()

    data class VideoItemClickedEvent(val videoListItem: VideoListItem) : VideoListEvents()
}