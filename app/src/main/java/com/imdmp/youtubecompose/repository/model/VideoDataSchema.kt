package com.imdmp.youtubecompose.repository.model

data class VideoDataSchema(
    val title: String,
    val views: Long,
    val uploadDate: String,
    val likeCount: Long,
    val uploaderName: String,
    val uploaderProfilePicUrl: String,
    val subscriberCount: Int,
    val videoDescription: String,
    val streamUrl: String
) {
}
