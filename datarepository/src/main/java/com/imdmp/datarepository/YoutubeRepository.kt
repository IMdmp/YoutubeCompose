package com.imdmp.datarepository

import com.imdmp.datarepository.model.YTDataSchema

interface YoutubeRepository {

    //YTData - corresponds to a video, short, community post or livestream
    fun getYTDataList(): YTDataSchema
}