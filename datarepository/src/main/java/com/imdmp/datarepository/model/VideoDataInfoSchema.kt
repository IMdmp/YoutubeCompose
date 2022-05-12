package com.imdmp.datarepository.model

import org.schabi.newpipe.extractor.stream.VideoStream

class VideoDataInfoSchema(
    val title: String,
    val views: Long,
    val uploadDate: String,
    val likeCount: Long,
    val uploaderName: String,
    val uploaderProfilePicUrl: String,
    val subscriberCount: Int,
    val videoDescription: String,
    val streamList: List<VideoStream>
) {

}
