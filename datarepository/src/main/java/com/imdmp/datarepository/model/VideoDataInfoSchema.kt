package com.imdmp.datarepository.model

class VideoDataInfoSchema(
    val title: String,
    val views: Long,
    val uploadDate: String,
    val likeCount: Long,
    val uploaderName: String,
    val uploaderProfilePicUrl: String,
    val subscriberCount: Int,
    val videoDescription: String,
    val streamList: List<VideoStreamSchema>
) {

}


data class VideoStreamSchema(
    val resolution: String,
    val isVideoOnly: Boolean,
    val quality: String,
    val codec: String,
    val url: String
)