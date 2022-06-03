package com.imdmp.youtubecompose.features.videoplayer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.Player
import com.imdmp.datarepository.YoutubeRepository
import com.imdmp.datarepository.model.VideoDataInfoSchema
import com.imdmp.datarepository.usecase.GetVideoStreamUrlUseCase
import com.imdmp.youtubecompose_ui.ui_player.VideoEvent
import com.imdmp.youtubecompose_ui.ui_player.model.PlayerStatus
import com.imdmp.youtubecompose_ui.ui_player.model.VideoPlayerComposeScreenState
import com.imdmp.youtubecompose_ui.ui_player.model.VideoPlayerScreenCallbacks
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
    val player: ExoPlayer,
    val dataRepository: YoutubeRepository
) : ViewModel(), VideoPlayerScreenCallbacks {

    val uiState = MutableStateFlow(VideoPlayerComposeScreenState.init())

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

    override fun prepareAndPlayVideoPlayer(url: String) {
        viewModelScope.launch(Dispatchers.IO) {
//            val videoInfo = dataRepository.getVideoDataInfo(url)
//            setUiState(videoInfo)
//
//            val mediaSource = getVideoStreamUrlUseCase(videoInfo.streamList.last())

            withContext(Dispatchers.Main) {
//                player.setMediaSource(mediaSource)
//                player.prepare()
//                player.play()
            }
        }
    }

    private fun setUiState(videoInfo: VideoDataInfoSchema) {
        uiState.value = uiState.value.copy(
            videoTitle = videoInfo.title,
            views = videoInfo.views,
            datePosted = videoInfo.uploadDate,
            likeCount = videoInfo.likeCount,
            authorUrl = videoInfo.uploaderProfilePicUrl,
            authorName = videoInfo.uploaderName,
            numberOfSubs = videoInfo.subscriberCount.toLong(),
            videoDescription = videoInfo.videoDescription
        )
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

    override fun retrieveComments() {
//        viewModelScope.launch(Dispatchers.IO) {
//            val comments = CommentsInfo.getInfo(NewPipe.getService(0), url)
//
//            Timber.d("test here. $comments ")
//
//            uiState.value =
//                uiState.value.copy(commentList = comments.relatedItems.map { commentInfoItem ->
//                    CommentModel(
//                        authorName = commentInfoItem.uploaderName,
//                        commentText = commentInfoItem.commentText,
//                        profilePic = commentInfoItem.uploaderAvatarUrl,
//                        likeCount = commentInfoItem.likeCount,
//                        timeCommented = commentInfoItem.textualUploadDate
//                    )
//                })
//
//        }
//    }
    }
}
