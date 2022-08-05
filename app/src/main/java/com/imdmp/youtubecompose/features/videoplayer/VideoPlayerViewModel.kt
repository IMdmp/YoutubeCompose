package com.imdmp.youtubecompose.features.videoplayer

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.imdmp.datarepository.YoutubeRepository
import com.imdmp.youtubecompose.base.BaseViewModel
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
    val videoMode = mutableStateOf(VideoMode.NORMAL)
    val url = mutableStateOf("")

    fun initVideoPlayer(url: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val videoData = youtubeRepository.getVideoDataInfo(url)
            val resUrl = videoData.streamList.last()
            val uri =
                Uri.parse(resUrl.url)
            val mediaItem: MediaItem = MediaItem.fromUri(uri)

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

    fun updateUrl(streamUrl: String) {
        url.value = streamUrl
    }
}

