package com.imdmp.youtubecompose.features.videoplayer

import com.imdmp.youtubecompose.features.videoplayer.desc.CommentModel

data class VideoPlayerComposeScreenState(
    val playerStatus: PlayerStatus = PlayerStatus.LOADING,
    val encryptedUrl: String = "",
    val commentList: List<CommentModel> = listOf(),
    val videoTitle: String,
    val views: Long,
    val datePosted: String,
    val likeCount: Long,
    val authorUrl: String,
    val authorName: String,
    val numberOfSubs: Long,
    val videoDescription: String,
    val streamList: List<StreamInfo>,
    val currentStreamInfo: StreamInfo,
) {
    companion object {
        fun init(): VideoPlayerComposeScreenState {
            return VideoPlayerComposeScreenState(
                playerStatus = PlayerStatus.IDLE,
                encryptedUrl = "",
                commentList = listOf(),
                videoTitle = "",
                views = 0,
                datePosted = "",
                likeCount = 0,
                authorUrl = "",
                authorName = "",
                numberOfSubs = 0,
                videoDescription = "",
                currentStreamInfo = StreamInfo("", ""),
                streamList = listOf()
            )
        }
    }
}

data class StreamInfo(
    val url: String,
    val resolution: String
)

enum class PlayerStatus {
    PLAYING, PAUSED, LOADING, IDLE, ERROR
}