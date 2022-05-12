package com.imdmp.youtubecompose_ui.ui_player.model

import com.imdmp.youtubecompose_ui.ui_player.comments.CommentModel

data class VideoPlayerComposeScreenState(
    val playerStatus: PlayerStatus = PlayerStatus.LOADING,
    val commentList: List<CommentModel> = listOf(),
    val videoTitle: String,
    val views: Long,
    val datePosted: String,
    val likeCount: Long,
    val authorUrl: String,
    val authorName: String,
    val numberOfSubs: Long,
    val videoDescription: String,
) {
    companion object {
        fun init(): VideoPlayerComposeScreenState {
            return VideoPlayerComposeScreenState(
                playerStatus = PlayerStatus.IDLE,
                commentList = listOf(),
                videoTitle = "",
                views = 0,
                datePosted = "",
                likeCount = 0,
                authorUrl = "",
                authorName = "",
                numberOfSubs = 0,
                videoDescription = "",
            )
        }

        fun forTesting(): VideoPlayerComposeScreenState {
            return init().copy(
                commentList = listOf(
                    CommentModel.forTesting(),
                    CommentModel.forTesting(),
                    CommentModel.forTesting(),
                    CommentModel.forTesting()

                ),
                videoTitle = "Sweet and Savory Babka",
                views = 865,
                datePosted = "2 weeks ago",
                likeCount = 55,
                authorUrl = "",
                authorName = "Binging with Babish",
                numberOfSubs = 1,
                videoDescription = "This is a sample video description",
            )
        }
    }
}

enum class PlayerStatus {
    PLAYING, PAUSED, LOADING, IDLE, ERROR
}
