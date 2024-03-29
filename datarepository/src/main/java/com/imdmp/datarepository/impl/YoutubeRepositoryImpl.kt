package com.imdmp.datarepository.impl

import com.imdmp.datarepository.NewPipeDataModelConverter
import com.imdmp.datarepository.NewPipeExtractorWrapper
import com.imdmp.datarepository.YoutubeRepository
import com.imdmp.datarepository.model.VideoDataCommentSchema
import com.imdmp.datarepository.model.VideoDataInfoSchema
import com.imdmp.datarepository.model.YTDataSchema
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.schabi.newpipe.extractor.InfoItem

class YoutubeRepositoryImpl(
    private val newPipeExtractorWrapper: NewPipeExtractorWrapper,
    private val newPipeDataModelConverter: NewPipeDataModelConverter,
) : YoutubeRepository {
    override suspend fun getYTDataList(): YTDataSchema {

        val info = newPipeExtractorWrapper.getInfo()
        val ytDataList =
            newPipeDataModelConverter.mapStreamInfoItemListToYtDataList(info.relatedItems as MutableList<InfoItem>)

        return YTDataSchema(ytDataList)
    }

    override suspend fun getVideoDataInfo(encryptedUrl: String): VideoDataInfoSchema {
        val streamInfo = newPipeExtractorWrapper.getVideoData(encryptedUrl)

        return newPipeDataModelConverter.mapStreamInfoToVideoDataInfoSchema(streamInfo)

    }

    override suspend fun getVideoDataComments(url: String): List<VideoDataCommentSchema> {
        val commentsInfo = newPipeExtractorWrapper.getComments(url)

        return newPipeDataModelConverter.mapCommentsInfoToVideoDataCommentSchemaList(commentsInfo)
    }

    override suspend fun search(query: String): YTDataSchema {
        val searchInfo = newPipeExtractorWrapper.search(query)

        return YTDataSchema(
            newPipeDataModelConverter.mapSearchInfoToVideoDataCommentSchemaList(
                searchInfo
            )
        )
    }

    override suspend fun searchAutoSuggestion(query: String): Flow<List<String>> {
        return flow {
            //todo: add local repo suggestions(previous searches)
            emit(newPipeExtractorWrapper.getSearchSuggestions(query))

        }
    }

    override suspend fun getComments(streamUrl: String): List<VideoDataCommentSchema> {
        return newPipeDataModelConverter.mapCommentsInfoToVideoDataCommentSchemaList(
            newPipeExtractorWrapper.getComments(streamUrl)
        )

    }
}
