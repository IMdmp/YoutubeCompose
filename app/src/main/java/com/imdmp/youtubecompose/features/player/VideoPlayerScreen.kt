package com.imdmp.youtubecompose.features.player

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

@Composable
fun VideoPlayerScreen(videoPlayerViewModel: VideoPlayerViewModel, streamUrl: String) {

    Box(modifier = Modifier.fillMaxSize()) {
        val context = LocalContext.current
        val player = SimpleExoPlayer.Builder(context).build().apply {
            addListener(object : Player.Listener {
                override fun onPlaybackStateChanged(state: Int) {
                    super.onPlaybackStateChanged(state)
                    Timber.d("state changed.")
                    if (state == Player.STATE_READY) {
                        videoPlayerViewModel.handleEvent(VideoEvent.VideoLoaded)
                    } else if (state == Player.EVENT_PLAYER_ERROR) {
                        videoPlayerViewModel.handleEvent(VideoEvent.VideoError)
                    }
                }
            })
        }


        val playerView = PlayerView(context)
        val playWhenReady by rememberSaveable {
            mutableStateOf(true)
        }
        playerView.player = player
        LaunchedEffect(Unit) {
            this.launch(Dispatchers.IO) {
                try {
                    Timber.d("test: stream url: $streamUrl")
                    val mediaSource = videoPlayerViewModel.getMediaSource(streamUrl)
                    withContext(Dispatchers.Main) {
                        player.setMediaSource(mediaSource)
                        player.prepare()
                        Timber.d("test: play when ready: ${player.playWhenReady}")
                        player.playWhenReady = playWhenReady
                        player.play()
                    }


                } catch (e: java.lang.Exception) {
                    e.printStackTrace()
                    Timber.d("ERRRO!")
                }
            }
        }

        AndroidView(factory = {
            playerView
        })
    }
}

