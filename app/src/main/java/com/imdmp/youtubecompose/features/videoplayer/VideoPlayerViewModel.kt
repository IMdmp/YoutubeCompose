package com.imdmp.youtubecompose.features.videoplayer

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.imdmp.youtubecompose.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class VideoPlayerViewModel @Inject constructor(var videoPlayer: ExoPlayer) : BaseViewModel(),
    VideoPlayerViewCallbacks {
    val videoMode = mutableStateOf(VideoMode.NORMAL)
    val url = mutableStateOf("")

    fun initVideoPlayer(url: String) {
        val uri =
            Uri.parse(url)

        val mediaItem: MediaItem = MediaItem.fromUri(uri)
        videoPlayer.setMediaItem(mediaItem)
        videoPlayer.prepare()
        videoPlayer.play()
    }

    override fun goFullScreen() {
        postViewModelEvent(VideoPlayerEvents.FullScreenPressed())
    }

    fun updateUrl(streamUrl: String) {
        url.value = streamUrl
    }
}

