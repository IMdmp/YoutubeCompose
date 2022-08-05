package com.imdmp.youtubecompose.features.videoplayer

import com.imdmp.youtubecompose.features.videolist.ViewModelEvent

sealed class VideoPlayerEvents : ViewModelEvent() {
    class FullScreenPressed : VideoPlayerEvents()
}