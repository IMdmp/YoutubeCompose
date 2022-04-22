package com.imdmp.datarepository.impl

import com.imdmp.datarepository.NewPipeDataModelConverter
import com.imdmp.datarepository.model.VideoDataCommentSchema
import com.imdmp.datarepository.model.VideoDataInfoSchema
import com.imdmp.datarepository.model.YTDataItem
import org.schabi.newpipe.extractor.InfoItem
import org.schabi.newpipe.extractor.channel.ChannelInfoItem
import org.schabi.newpipe.extractor.comments.CommentsInfo
import org.schabi.newpipe.extractor.playlist.PlaylistInfoItem
import org.schabi.newpipe.extractor.stream.StreamInfo
import org.schabi.newpipe.extractor.stream.StreamInfoItem

class NewPipeDataModelConverterImpl : NewPipeDataModelConverter {
    override fun mapStreamInfoItemListToYtDataList(relatedItems: MutableList<StreamInfoItem>): List<YTDataItem> {

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
        return VideoDataInfoSchema(streamInfo.url, streamInfo.name)
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
}