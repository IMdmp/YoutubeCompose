package com.imdmp.datarepository

import org.schabi.newpipe.extractor.comments.CommentsInfo
import org.schabi.newpipe.extractor.kiosk.KioskInfo
import org.schabi.newpipe.extractor.stream.StreamInfo

interface NewPipeExtractorWrapper {

    fun getInfo(): KioskInfo
    fun getVideoData(encryptedStreamUrl: String): StreamInfo
    fun getComments(url: String): CommentsInfo
    fun getSearchSuggestions(query: String): MutableList<String>
}