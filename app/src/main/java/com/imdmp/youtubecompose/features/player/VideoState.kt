package com.imdmp.youtubecompose.features.player

data class VideoState(
    val playerStatus: PlayerStatus = PlayerStatus.LOADING
) {
}

enum class PlayerStatus {
    PLAYING, PAUSED, LOADING, IDLE, ERROR
}