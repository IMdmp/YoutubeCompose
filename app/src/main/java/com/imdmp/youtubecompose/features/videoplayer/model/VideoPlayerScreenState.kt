package com.imdmp.youtubecompose.features.videoplayer.model

data class VideoPlayerScreenState(
    val playerStatus: PlayerStatus = PlayerStatus.LOADING,
    val streamUrl:String = "",
    val commentList:List<String> = listOf()
) {
}

enum class PlayerStatus {
    PLAYING, PAUSED, LOADING, IDLE, ERROR
}