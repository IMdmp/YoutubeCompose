package com.imdmp.youtubecompose.features.videoplayer

sealed class VideoEvent {
    object ToggleStatus : VideoEvent()

    object VideoLoaded : VideoEvent()

    object VideoError: VideoEvent()

    object VideoLoading:VideoEvent()
}