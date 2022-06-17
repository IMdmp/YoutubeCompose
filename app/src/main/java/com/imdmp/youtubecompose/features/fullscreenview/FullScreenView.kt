package com.imdmp.youtubecompose.features.fullscreenview

import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.ui.PlayerView
import com.imdmp.youtubecompose.features.videoplayer.VideoPlayerViewModel

@Composable
fun FullScreenView(
    modifier: Modifier = Modifier,
    navController: NavController,
    videoPlayerViewModel: VideoPlayerViewModel,
    player: ExoPlayer,
    playerScreenCallbacks: com.imdmp.videoplayer.model.VideoPlayerScreenCallbacks
) {

    MaterialTheme {
        val context = LocalContext.current
        LaunchedEffect(player) {

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
