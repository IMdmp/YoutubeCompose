package com.imdmp.datarepository

import com.imdmp.datarepository.model.VideoDataCommentSchema
import com.imdmp.datarepository.model.VideoDataInfoSchema
import com.imdmp.datarepository.model.YTDataItem
import org.schabi.newpipe.extractor.InfoItem
import org.schabi.newpipe.extractor.comments.CommentsInfo
import org.schabi.newpipe.extractor.search.SearchInfo
import org.schabi.newpipe.extractor.stream.StreamInfo

interface NewPipeDataModelConverter {

    fun mapStreamInfoItemListToYtDataList(relatedItems: MutableList<InfoItem>): List<YTDataItem>
    fun mapStreamInfoToVideoDataInfoSchema(streamInfo: StreamInfo): VideoDataInfoSchema
    fun mapCommentsInfoToVideoDataCommentSchemaList(commentsInfo: CommentsInfo): List<VideoDataCommentSchema>
    fun mapSearchInfoToVideoDataCommentSchemaList(searchInfo: SearchInfo): List<YTDataItem>
}