package com.imdmp.datarepository.usecase

import com.google.android.exoplayer2.source.MediaSource
import org.schabi.newpipe.extractor.stream.VideoStream

interface GetVideoStreamUrlUseCase {
    suspend operator fun invoke(url: VideoStream): MediaSource
}
