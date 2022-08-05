package com.imdmp.videoplayer

sealed class VideoEvent {
    object ToggleStatus : VideoEvent()

    object VideoLoaded : VideoEvent()

    object VideoError: VideoEvent()

    object VideoLoading: VideoEvent()
}
