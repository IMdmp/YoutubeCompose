package com.imdmp.datarepository

import org.schabi.newpipe.extractor.kiosk.KioskInfo

interface NewPipeExtractorWrapper {

    fun getInfo(): KioskInfo
}