package com.imdmp.youtubecompose.features.player

import androidx.lifecycle.ViewModel
import com.google.android.exoplayer2.source.MediaSource
import com.imdmp.youtubecompose.usecases.GetVideoStreamUrlUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class VideoPlayerViewModel @Inject constructor(
    private val getVideoStreamUrlUseCase: GetVideoStreamUrlUseCase
) : ViewModel() {

    suspend fun getMediaSource(streamUrl: String): MediaSource {
        return getVideoStreamUrlUseCase.invoke(streamUrl)
    }
}