package com.imdmp.youtubecompose.features.videoplayer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.Player
import com.imdmp.datarepository.YoutubeRepository
import com.imdmp.datarepository.model.VideoDataInfoSchema
import com.imdmp.datarepository.usecase.GetVideoStreamUrlUseCase
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
) : ViewModel(), com.imdmp.videoplayer.model.VideoPlayerScreenCallbacks {

    val uiState = MutableStateFlow(com.imdmp.videoplayer.model.VideoPlayerComposeScreenState.init())

    init {
        player.addListener(object : Player.Listener {
            override fun onPlaybackStateChanged(state: Int) {
                super.onPlaybackStateChanged(state)

                when (state) {
                    Player.STATE_READY -> {
                        handleEvent(com.imdmp.videoplayer.VideoEvent.VideoLoaded)
                    }
                    Player.STATE_BUFFERING -> {
                        handleEvent(com.imdmp.videoplayer.VideoEvent.VideoLoading)
                    }
                }
            }
        }
        )
    }

    override fun prepareAndPlayVideoPlayer(url: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val videoInfo = dataRepository.getVideoDataInfo(url)
            setUiState(videoInfo)

            val mediaSource = getVideoStreamUrlUseCase(videoInfo.streamList.last())

            withContext(Dispatchers.Main) {
                player.setMediaSource(mediaSource)
                player.prepare()
                player.play()
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
        handleEvent(com.imdmp.videoplayer.VideoEvent.ToggleStatus)
    }

    fun handleEvent(videoEvent: com.imdmp.videoplayer.VideoEvent) {
        when (videoEvent) {
            com.imdmp.videoplayer.VideoEvent.VideoError -> {
                Timber.d("video error!")
                uiState.value =
                    uiState.value.copy(playerStatus = com.imdmp.videoplayer.model.PlayerStatus.ERROR)
            }
            com.imdmp.videoplayer.VideoEvent.VideoLoaded -> {
                Timber.d("video loaded!")
                uiState.value =
                    uiState.value.copy(playerStatus = com.imdmp.videoplayer.model.PlayerStatus.IDLE)

            }

            com.imdmp.videoplayer.VideoEvent.VideoLoading -> {
                uiState.value =
                    uiState.value.copy(playerStatus = com.imdmp.videoplayer.model.PlayerStatus.LOADING)
            }

            com.imdmp.videoplayer.VideoEvent.ToggleStatus -> {
                togglePlayerStatus()
            }
        }
    }

    private fun togglePlayerStatus() {
        val playerStatus = uiState.value.playerStatus

        val newPlayerStatus = if (
            playerStatus != com.imdmp.videoplayer.model.PlayerStatus.PLAYING) {
            com.imdmp.videoplayer.model.PlayerStatus.PLAYING
        } else
            com.imdmp.videoplayer.model.PlayerStatus.PAUSED

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
