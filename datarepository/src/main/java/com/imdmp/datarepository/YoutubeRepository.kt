package com.imdmp.datarepository

import com.imdmp.datarepository.model.VideoDataCommentSchema
import com.imdmp.datarepository.model.VideoDataInfoSchema
import com.imdmp.datarepository.model.YTDataSchema
import kotlinx.coroutines.flow.Flow

interface YoutubeRepository {

    //YTData - corresponds to a video, short, community post or livestream
    suspend fun getYTDataList(): YTDataSchema

    suspend fun getVideoDataInfo(encryptedUrl: String): VideoDataInfoSchema

    suspend fun getVideoDataComments(url: String): List<VideoDataCommentSchema>

    suspend fun search(query: String): YTDataSchema

    suspend fun searchAutoSuggestion(query: String): Flow<List<String>>
}
