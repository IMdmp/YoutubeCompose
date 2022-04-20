package com.imdmp.datarepository.model

data class YTDataSchema(
    val ytDataList: List<YTDataItem>

) {
}

data class YTDataItem(
    val url: String,
    val thumbnail: String,
    val name: String,
    val uploaderThumbnail: String?,
    val uploaderName: String,
    val viewCount: Long,
)