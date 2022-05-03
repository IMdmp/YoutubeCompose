package com.imdmp.datarepository.impl

import com.imdmp.datarepository.NewPipeExtractorWrapper
import org.schabi.newpipe.extractor.NewPipe
import org.schabi.newpipe.extractor.comments.CommentsInfo
import org.schabi.newpipe.extractor.kiosk.KioskInfo
import org.schabi.newpipe.extractor.services.youtube.YoutubeService
import org.schabi.newpipe.extractor.stream.StreamInfo
import org.schabi.newpipe.extractor.suggestion.SuggestionExtractor

class NewPipeExtractorWrapperImpl : NewPipeExtractorWrapper {
    override fun getInfo(): KioskInfo {
        return KioskInfo.getInfo(YoutubeService(0), "https://www.youtube.com/feed/trending")
    }

    override fun getVideoData(encryptedStreamUrl: String): StreamInfo {
        return StreamInfo.getInfo(NewPipe.getService(0), encryptedStreamUrl)
    }

    override fun getComments(url: String): CommentsInfo {
        return CommentsInfo.getInfo(NewPipe.getService(0), url)
    }

    override fun getSearchSuggestions(query: String): MutableList<String> {
        val extractor: SuggestionExtractor = NewPipe.getService(0)
            .suggestionExtractor

        return extractor.suggestionList(query)
    }
}