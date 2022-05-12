package com.imdmp.youtubecompose_ui.ui_player.playback

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidViewBinding
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.ui.PlayerView
import com.imdmp.youtubecompose_ui.databinding.CustomPlayerViewBinding

@Composable
fun Playback(
    modifier: Modifier = Modifier,
    player: ExoPlayer?,
    playerScreenCallbacks: PlaybackScreenCallbacks,
    onUpdate: (PlayerView) -> Unit = {}
) {
    val context = LocalContext.current

    DisposableEffect(
        AndroidViewBinding(
            modifier = modifier.fillMaxSize(),
            factory = CustomPlayerViewBinding::inflate
        ) {
            this.playerView.player = player
            onUpdate(this.playerView)
        }
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
