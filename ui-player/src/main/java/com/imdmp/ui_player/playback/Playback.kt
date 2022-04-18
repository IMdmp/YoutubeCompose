package com.imdmp.ui_player.playback

import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.ui.PlayerView

@Composable
fun Playback(
    modifier: Modifier = Modifier,
    player: ExoPlayer?,
    playerScreenCallbacks: PlaybackScreenCallbacks,
    onUpdate: (PlayerView) -> Unit = {}
) {
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
                    this.useController = false
                    this.hideController()
                    this.player = player
                }
            },
            update = onUpdate
        )
    ) {
        onDispose {
            playerScreenCallbacks.disposeVideoPlayer()
        }
    }
}


@Preview
@Composable
fun PreviewPlayback() {
    Playback(
        player = null,
        playerScreenCallbacks = PlaybackScreenCallbacks.default()
    )
}