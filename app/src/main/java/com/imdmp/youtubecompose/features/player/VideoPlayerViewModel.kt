package com.imdmp.youtubecompose.features.player

import androidx.lifecycle.ViewModel
import com.google.android.exoplayer2.source.MediaSource
import com.imdmp.youtubecompose.usecases.GetVideoStreamUrlUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class VideoPlayerViewModel @Inject constructor(
    private val getVideoStreamUrlUseCase: GetVideoStreamUrlUseCase
) : ViewModel() {

    val uiState = MutableStateFlow(VideoState())

    suspend fun getMediaSource(streamUrl: String): MediaSource {
        return getVideoStreamUrlUseCase(streamUrl)
    }

    fun handleEvent(videoEvent: VideoEvent) {
        when (videoEvent) {
            VideoEvent.VideoError -> {
                Timber.d("video error!")
                uiState.value = uiState.value.copy(playerStatus = PlayerStatus.ERROR)
            }
            VideoEvent.VideoLoaded -> {
                Timber.d("video loaded!")
            }
            VideoEvent.ToggleStatus -> {
                togglePlayerStatus()
            }
        }
    }

    private fun togglePlayerStatus() {
        val playerStatus = uiState.value.playerStatus

        val newPlayerStatus = if (
            playerStatus != PlayerStatus.PLAYING) {
            PlayerStatus.PLAYING
        } else
            PlayerStatus.PAUSED

        uiState.value = uiState.value.copy(playerStatus = newPlayerStatus)
    }
}