package com.imdmp.youtubecompose.features.videoplayer

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.google.android.exoplayer2.ExoPlayer
import com.imdmp.youtubecompose.base.TestTags

@Composable
fun HandleLifecycleChanges(
    lifecycleOwner: LifecycleOwner,
    exoPlayer: ExoPlayer?
) {
    Box(
        modifier = Modifier
            .testTag(TestTags.TAG_PLAYER_LIFECYCLE_HANDLER)
            .fillMaxSize()
    ) {

    }
    if (exoPlayer == null)
        return

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (exoPlayer.isPlaying) {
                if (event == Lifecycle.Event.ON_PAUSE) {
                    exoPlayer.pause()
                }
            } else {
                if (event == Lifecycle.Event.ON_RESUME) {
                    exoPlayer.play()
                }
            }

        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
}