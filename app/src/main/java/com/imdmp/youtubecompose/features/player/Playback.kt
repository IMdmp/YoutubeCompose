package com.imdmp.youtubecompose.features.player

import android.content.Context
import android.view.ViewGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.ui.PlayerView
import com.imdmp.youtubecompose.base.Tags
import com.imdmp.youtubecompose.features.navigation.model.Destination
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

interface PlaybackScreenCallbacks{
    fun prepareAndPlayVideoPlayer(url:String="")

    fun disposeVideoPlayer()
}

@Composable
fun Playback(
    modifier: Modifier = Modifier,
    player: ExoPlayer?,
    playerScreenCallbacks: PlaybackScreenCallbacks,
) {
    MaterialTheme {
        val context = LocalContext.current
        LaunchedEffect(player) {
            playerScreenCallbacks.prepareAndPlayVideoPlayer()
        }

        DisposableEffect(
            AndroidView(
                modifier = modifier.fillMaxSize(),
                factory = {
                    PlayerView(context).apply {
                        layoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
                        )
                        this.player = player
                    }
                },
                update = {

                }
            )
        ) {
            onDispose {
                playerScreenCallbacks.disposeVideoPlayer()
            }
        }
    }
}

@Preview
@Composable
fun PreviewPlayback() {
    Playback(player = null, playerScreenCallbacks = VideoPlayerScreenCallbacks.default())
}