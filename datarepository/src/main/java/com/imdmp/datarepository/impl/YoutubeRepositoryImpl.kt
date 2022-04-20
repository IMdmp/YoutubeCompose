package com.imdmp.datarepository.impl

import com.imdmp.datarepository.KioskInfoConverter
import com.imdmp.datarepository.NewPipeExtractorWrapper
import com.imdmp.datarepository.YoutubeRepository
import com.imdmp.datarepository.model.YTDataSchema

class YoutubeRepositoryImpl(
    private val newPipeExtractorWrapper: NewPipeExtractorWrapper,
    private val kioskInfoConverter: KioskInfoConverter
) : YoutubeRepository {
    override fun getYTDataList(): YTDataSchema {

        val info = newPipeExtractorWrapper.getInfo()
        val ytDataList = kioskInfoConverter.mapToYtDataList(info.relatedItems)

        return YTDataSchema(ytDataList)
    }
}