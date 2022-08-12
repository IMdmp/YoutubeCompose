package com.imdmp.youtubecompose.features.videoplayer

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.imdmp.datarepository.YoutubeRepository
import com.imdmp.youtubecompose.base.BaseViewModel
import com.imdmp.youtubecompose.features.videoplayer.desc.CommentModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class VideoPlayerViewModel @Inject constructor(
    var videoPlayer: ExoPlayer,
    val youtubeRepository: YoutubeRepository
) : BaseViewModel(),
    VideoPlayerViewCallbacks {

    private val state = mutableStateOf(VideoPlayerComposeScreenState.init())

    fun getState(): VideoPlayerComposeScreenState {
        return state.value
    }

    private fun initVideoPlayerData(encryptedUrl: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val videoData = youtubeRepository.getVideoDataInfo(encryptedUrl)
            state.value = state.value.copy(
                encryptedUrl = encryptedUrl,
                videoDescription = videoData.videoDescription,
                videoTitle = videoData.title,
                views = videoData.views,
                datePosted = videoData.uploadDate,
                likeCount = videoData.likeCount,
                authorUrl = videoData.uploaderProfilePicUrl,
                authorName = videoData.uploaderName,
                numberOfSubs = videoData.subscriberCount.toLong(),
                streamList = videoData.streamList.map {
                    StreamInfo(
                        it.url,
                        it.resolution
                    )
                },
                currentStreamInfo = with(videoData.streamList.last()) {
                    StreamInfo(this.url, this.resolution)
                }
            )
        }
    }

    fun initVideoPlayer(url: String) {
        val uri =
            Uri.parse(url)
        val mediaItem: MediaItem = MediaItem.fromUri(uri)
        viewModelScope.launch {
            withContext(Dispatchers.Main) {
                videoPlayer.setMediaItem(mediaItem)
                videoPlayer.prepare()
                videoPlayer.play()
            }
        }
    }


    override fun goFullScreen() {
        postViewModelEvent(VideoPlayerEvents.FullScreenPressed())
    }

    override fun closeButtonClicked() {
        postViewModelEvent(VideoPlayerEvents.CloseButtonPressed())
    }

    override fun pausePlayClicked() {
        TODO("Not yet implemented")
    }

    fun updateUrl(streamUrl: String) {
        initVideoPlayerData(streamUrl)
        initComments(streamUrl)
    }

    private fun initComments(streamUrl: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val comments = youtubeRepository.getComments(streamUrl = streamUrl)
            state.value = state.value.copy(
                commentList = comments.map {
                    CommentModel(
                        it.name,
                        it.comment,
                        it.profilePicUrl,
                        null,
                        null
                    )
                }
            )
        }
    }

    fun onStart() {
    }

    fun onStop() {
        videoPlayer.stop()
    }
}

