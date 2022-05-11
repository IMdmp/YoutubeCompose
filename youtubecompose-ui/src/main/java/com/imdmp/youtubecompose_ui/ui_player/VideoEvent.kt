package com.imdmp.youtubecompose_ui.ui_player

sealed class VideoEvent {
    object ToggleStatus : VideoEvent()

    object VideoLoaded : VideoEvent()

    object VideoError: VideoEvent()

    object VideoLoading: VideoEvent()
}