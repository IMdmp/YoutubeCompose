package com.imdmp.youtubecompose.features.videoplayer.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.source.MediaSource
import com.imdmp.youtubecompose.features.videoplayer.VideoEvent
import com.imdmp.youtubecompose.features.videoplayer.comments.CommentModel
import com.imdmp.youtubecompose.usecases.GetVideoStreamUrlUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.schabi.newpipe.extractor.NewPipe
import org.schabi.newpipe.extractor.comments.CommentsInfo
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class VideoPlayerViewModel @Inject constructor(
    private val getVideoStreamUrlUseCase: GetVideoStreamUrlUseCase,
    val player: ExoPlayer
) : ViewModel(), VideoPlayerScreenCallbacks {

    init {
        player.addListener(object : Player.Listener {
            override fun onPlaybackStateChanged(state: Int) {
                super.onPlaybackStateChanged(state)

                when (state) {
                    Player.STATE_READY -> {
                        handleEvent(VideoEvent.VideoLoaded)
                    }
                    Player.STATE_BUFFERING -> {
                        handleEvent(VideoEvent.VideoLoading)
                    }
                }
            }
        }
        )
    }

    val uiState = MutableStateFlow(VideoPlayerScreenState.init())

    override suspend fun getMediaSource(url: String): MediaSource {
        return getVideoStreamUrlUseCase(url)
    }

    override fun prepareAndPlayVideoPlayer(url: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val urlToUse =
                if (url.isEmpty()) {
                    uiState.value.streamUrl
                } else {
                    url
                }

            val mediaSource = getMediaSource(urlToUse)

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

    override fun fullScreenClicked() {

    }

    override fun pauseOrPlayClicked() {
        handleEvent(VideoEvent.ToggleStatus)
    }

    fun handleEvent(videoEvent: VideoEvent) {
        when (videoEvent) {
            VideoEvent.VideoError -> {
                Timber.d("video error!")
                uiState.value = uiState.value.copy(playerStatus = PlayerStatus.ERROR)
            }
            VideoEvent.VideoLoaded -> {
                Timber.d("video loaded!")
                uiState.value = uiState.value.copy(playerStatus = PlayerStatus.IDLE)

            }

            VideoEvent.VideoLoading -> {
                uiState.value = uiState.value.copy(playerStatus = PlayerStatus.LOADING)
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

    override fun retrieveComments() {
        val url = uiState.value.streamUrl
        viewModelScope.launch(Dispatchers.IO) {
            val comments = CommentsInfo.getInfo(NewPipe.getService(0), url)

            Timber.d("test here. $comments ")

            uiState.value =
                uiState.value.copy(commentList = comments.relatedItems.map { commentInfoItem ->
                    CommentModel(
                        authorName = commentInfoItem.uploaderName,
                        commentText = commentInfoItem.commentText,
                        profilePic = commentInfoItem.uploaderAvatarUrl,
                        likeCount = commentInfoItem.likeCount,
                        timeCommented = commentInfoItem.textualUploadDate
                    )
                })

        }
    }

}