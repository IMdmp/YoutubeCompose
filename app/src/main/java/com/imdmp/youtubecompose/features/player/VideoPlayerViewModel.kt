package com.imdmp.youtubecompose.features.player

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.imdmp.youtubecompose.usecases.GetVideoStreamUrlUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class VideoPlayerViewModel @Inject constructor(
    private val getVideoStreamUrlUseCase: GetVideoStreamUrlUseCase,
    val player: ExoPlayer
) : ViewModel(), VideoPlayerScreenCallbacks {

    val uiState = MutableStateFlow(VideoPlayerScreenState())

    override suspend fun getMediaSource(url: String): MediaSource {
        return getVideoStreamUrlUseCase(url)
    }

    override fun prepareAndPlayVideoPlayer(url: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val mediaSource = getMediaSource(url)

            withContext(Dispatchers.Main) {
                player.setMediaSource(mediaSource)
                player.prepare()
                player.play()
            }
        }
    }

    override fun disposeVideoPlayer() {
        player.stop()
    }

    override fun selectFullScreen() {
        TODO("Not yet implemented")
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

    fun updateUrl(streamUrl: String) {
        uiState.value = uiState.value.copy(streamUrl = streamUrl)
    }

}