package com.imdmp.youtubecompose.usecases

import com.google.android.exoplayer2.source.MediaSource

interface GetVideoStreamUrlUseCase {
    suspend operator fun invoke(encryptedStreamUrl:String):MediaSource
}