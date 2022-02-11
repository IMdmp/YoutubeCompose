package com.imdmp.youtubecompose.features.player

sealed class VideoEvent {
    object ToggleStatus:VideoEvent()

    object VideoLoaded:VideoEvent()

    object VideoError: VideoEvent()
}