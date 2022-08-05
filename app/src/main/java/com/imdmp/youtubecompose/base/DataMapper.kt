package com.imdmp.youtubecompose.base

import com.imdmp.datarepository.model.YTDataSchema
import com.imdmp.youtubecompose_ui.uihome.VideoListItem

fun YTDataSchema.mapToVideoListItem(): List<VideoListItem> {
    return this.ytDataList.map {
        VideoListItem(
            imageUrl = it.thumbnail,
            title = it.name,
            author = it.uploaderName,
            authorImageUrl = it.uploaderThumbnail,
            viewCount = it.viewCount,
            uploadedDate = "",
            streamUrl = it.url
        )
    }
}


