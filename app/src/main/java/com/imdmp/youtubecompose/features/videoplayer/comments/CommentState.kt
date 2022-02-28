package com.imdmp.youtubecompose.features.videoplayer.comments

data class CommentState(
    val commentModelList: List<CommentModel>,
    val isLoading: Boolean,
    val error: Error? = null
) {

}

data class CommentModel(
    val authorName: String,
    val commentText: String,
    val profilePic: String,
    val likeCount: Long,
    val timeCommented: String,
)