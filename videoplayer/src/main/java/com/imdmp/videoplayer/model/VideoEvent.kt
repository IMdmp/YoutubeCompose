package com.imdmp.videoplayer.model

sealed class VideoEvent {
    object ToggleStatus : VideoEvent()

    object VideoLoaded : VideoEvent()

    object VideoError : VideoEvent()

    object VideoLoading : VideoEvent()
}
