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
        val player = SimpleExoPlayer.Builder(context).build()
        val playerView = PlayerView(context)
        val playWhenReady by rememberSaveable {
            mutableStateOf(true)
        }
        playerView.player = player
        LaunchedEffect(player) {
            this.launch(Dispatchers.IO) {
                val mediaSource = videoPlayerViewModel.getMediaSource(streamUrl)
                withContext(Dispatchers.Main) {
                    Timber.d("media Source : $mediaSource")
                    player.setMediaSource(mediaSource)
                    player.prepare()
                    player.playWhenReady = playWhenReady
                }

            }


        }

        AndroidView(factory = {
            playerView
        })
    }
}
