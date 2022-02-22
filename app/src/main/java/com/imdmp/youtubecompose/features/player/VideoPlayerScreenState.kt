package com.imdmp.youtubecompose.features.player

data class VideoPlayerScreenState(
    val playerStatus: PlayerStatus = PlayerStatus.LOADING,
    val streamUrl:String = "",
    val commentList:List<String> = listOf()
) {
}

enum class PlayerStatus {
    PLAYING, PAUSED, LOADING, IDLE, ERROR
}