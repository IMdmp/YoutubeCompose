package com.imdmp.youtubecompose.features.videoplayer.desc

data class CommentState(
    val commentModelList: List<CommentModel>,
    val isLoading: Boolean,
    val error: Error? = null
) {

}

data class CommentModel(
    val authorName: String,
    val commentText: String,
    val profilePic: String?,
    val likeCount: Int?,
    val timeCommented: String?,
)