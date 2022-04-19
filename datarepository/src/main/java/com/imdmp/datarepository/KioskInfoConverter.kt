package com.imdmp.datarepository

import com.imdmp.datarepository.model.YTDataItem
import org.schabi.newpipe.extractor.stream.StreamInfoItem

interface KioskInfoConverter {

    fun mapToYtDataList(relatedItems: MutableList<StreamInfoItem>): List<YTDataItem>
}