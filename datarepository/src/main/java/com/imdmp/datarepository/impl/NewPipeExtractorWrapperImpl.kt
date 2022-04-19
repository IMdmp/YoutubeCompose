package com.imdmp.datarepository.impl

import com.imdmp.datarepository.NewPipeExtractorWrapper
import org.schabi.newpipe.extractor.kiosk.KioskInfo
import org.schabi.newpipe.extractor.services.youtube.YoutubeService

class NewPipeExtractorWrapperImpl : NewPipeExtractorWrapper {
    override fun getInfo(): KioskInfo {
        return KioskInfo.getInfo(YoutubeService(0), "https://www.youtube.com/feed/trending")
    }
}