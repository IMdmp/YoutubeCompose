package com.imdmp.youtubecompose.features.videoplayer.model

import com.imdmp.youtubecompose.features.videoplayer.comments.CommentModel

data class VideoPlayerScreenState(
    val playerStatus: PlayerStatus = PlayerStatus.LOADING,
    val streamUrl:String = "",
    val commentList:List<CommentModel> = listOf(),
    val videoTitle:String,
    val views:Long,
    val datePosted:String,
    val likeCount:Long,
    val authorUrl:String,
    val authorName:String,
    val numberOfSubs:Long,
    val videoDescription:String,
) {
    companion object{
        fun init():VideoPlayerScreenState{
            return VideoPlayerScreenState(
                playerStatus =PlayerStatus.IDLE,
                streamUrl = "",
                commentList = listOf(),
                videoTitle = "",
                views = 0,
                datePosted = "",
                likeCount = 0,
                authorUrl = "",
                authorName = "",
                numberOfSubs = 0,
                videoDescription = ""
            )
        }
    }
}

enum class PlayerStatus {
    PLAYING, PAUSED, LOADING, IDLE, ERROR
}