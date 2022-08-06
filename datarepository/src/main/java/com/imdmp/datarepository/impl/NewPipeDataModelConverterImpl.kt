package com.imdmp.datarepository.impl

import com.imdmp.datarepository.NewPipeDataModelConverter
import com.imdmp.datarepository.model.VideoDataCommentSchema
import com.imdmp.datarepository.model.VideoDataInfoSchema
import com.imdmp.datarepository.model.YTDataItem
import org.schabi.newpipe.extractor.InfoItem
import org.schabi.newpipe.extractor.channel.ChannelInfoItem
import org.schabi.newpipe.extractor.comments.CommentsInfo
import org.schabi.newpipe.extractor.playlist.PlaylistInfoItem
import org.schabi.newpipe.extractor.search.SearchInfo
import org.schabi.newpipe.extractor.stream.StreamInfo
import org.schabi.newpipe.extractor.stream.StreamInfoItem

class NewPipeDataModelConverterImpl : NewPipeDataModelConverter {
    override fun mapStreamInfoItemListToYtDataList(relatedItems: MutableList<InfoItem>): List<YTDataItem> {

        return relatedItems.mapNotNull { infoItem ->
            when (infoItem.infoType) {
                InfoItem.InfoType.STREAM -> {
                    infoItem as StreamInfoItem
                    YTDataItem(
                        thumbnail = infoItem.thumbnailUrl,
                        name = infoItem.name,
                        uploaderName = infoItem.uploaderName,
                        uploaderThumbnail = infoItem.uploaderAvatarUrl,
                        viewCount = infoItem.viewCount,
                        url = infoItem.url
                    )
                }
                InfoItem.InfoType.PLAYLIST -> {
                    infoItem as PlaylistInfoItem
                    //todo
                    null
                }
                InfoItem.InfoType.CHANNEL -> {
                    infoItem as ChannelInfoItem
                    //todo
                    null
                }
                InfoItem.InfoType.COMMENT -> {
                    //todo

                    null
                }

                else -> {
                    requireNotNull(infoItem.infoType)
                    { "info item info type is null. Please make sure it's set!" }
                    //todo

                    null
                }
            }
        }
    }

    override fun mapStreamInfoToVideoDataInfoSchema(streamInfo: StreamInfo): VideoDataInfoSchema {
        return VideoDataInfoSchema(
            title = streamInfo.name,
            views = streamInfo.viewCount,
            uploadDate = streamInfo.textualUploadDate ?: "",
            likeCount = streamInfo.likeCount,
            uploaderName = streamInfo.uploaderName,
            uploaderProfilePicUrl = streamInfo.uploaderAvatarUrl,
            subscriberCount = 100000,
            videoDescription = streamInfo.description?.content ?: "",
            streamList = streamInfo.videoStreams
        )
    }

    override fun mapCommentsInfoToVideoDataCommentSchemaList(commentsInfo: CommentsInfo): List<VideoDataCommentSchema> {
        return commentsInfo.relatedItems.map {
            VideoDataCommentSchema(
                name = it.uploaderName,
                profilePicUrl = it.uploaderAvatarUrl,
                comment = it.commentText
            )
        }

    }

    override fun mapSearchInfoToVideoDataCommentSchemaList(searchInfo: SearchInfo): List<YTDataItem> {
        return searchInfo.relatedItems.mapToYTDataItemList()
    }
}


fun MutableList<InfoItem>.mapToYTDataItemList(): List<YTDataItem> {
    return this.mapNotNull { infoItem ->
        when (infoItem.infoType) {
            InfoItem.InfoType.STREAM -> {
                infoItem as StreamInfoItem
                YTDataItem(
                    thumbnail = infoItem.thumbnailUrl,
                    name = infoItem.name,
                    uploaderName = infoItem.uploaderName,
                    uploaderThumbnail = infoItem.uploaderAvatarUrl,
                    viewCount = infoItem.viewCount,
                    url = infoItem.url
                )
            }
            InfoItem.InfoType.PLAYLIST -> {
                infoItem as PlaylistInfoItem
                //todo
                null
            }
            InfoItem.InfoType.CHANNEL -> {
                infoItem as ChannelInfoItem
                //todo
                null
            }
            InfoItem.InfoType.COMMENT -> {
                //todo

                null
            }

            else -> {
                requireNotNull(infoItem.infoType)
                { "info item info type is null. Please make sure it's set!" }
                //todo

                null
            }
        }
    }
}