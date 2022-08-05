package com.imdmp.youtubecompose.features.videoplayer

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.viewinterop.AndroidViewBinding
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.imdmp.youtubecompose.R
import com.imdmp.youtubecompose.databinding.VideoPlayerBinding
import com.mikepenz.iconics.view.IconicsImageView
import kotlinx.coroutines.launch


@Composable
fun DraggableVideoPlayer(
    modifier: Modifier = Modifier,
    videoPlayerViewModel: VideoPlayerViewModel,
    content: @Composable () -> Unit = { Surface {} }
) {
    LaunchedEffect(videoPlayerViewModel.url.value) {
        videoPlayerViewModel.initVideoPlayer(videoPlayerViewModel.url.value)
    }
    DraggableVidPlayer(
        modifier = modifier,
        exoPlayer = videoPlayerViewModel.videoPlayer,
        videoMode = videoPlayerViewModel.videoMode,
        videoPlayerViewCallbacks = videoPlayerViewModel,
        content = content
    )
}

@Composable
fun DraggableVidPlayer(
    modifier: Modifier = Modifier,
    exoPlayer: ExoPlayer,
    videoMode: MutableState<VideoMode>,
    videoPlayerViewCallbacks: VideoPlayerViewCallbacks? = null,
    content: @Composable () -> Unit = { Surface {} }
) {
    var playerView1: StyledPlayerView? = null
    val screenMotionProgress = remember {
        Animatable(0f)
    }
    var offsetY by remember { mutableStateOf(0f) }

    val scope = rememberCoroutineScope()
    val windowState = remember { mutableStateOf(PlayerWindowState.NORMAL) }
    val draggableState = rememberDraggableState {
        offsetY += it
        scope.launch {
            screenMotionProgress.snapTo((offsetY / 1000).coerceAtLeast(0f).coerceAtMost(1f))

        }
    }

    LaunchedEffect(key1 = windowState.value) {
        playerView1?.useController = windowState.value != PlayerWindowState.COLLAPSED
    }

    AndroidViewBinding(
        modifier = modifier
            .fillMaxSize()
            .draggable(
                orientation = Orientation.Vertical,
                state = draggableState,
                onDragStopped = {
                    if (windowState.value == PlayerWindowState.NORMAL) {
                        offsetY = if (screenMotionProgress.value > 0.1f) {
                            screenMotionProgress.animateTo(1f).endState.value
                            windowState.value = PlayerWindowState.COLLAPSED
                            1000f
                        } else {
                            screenMotionProgress.animateTo(0f)
                            0f
                        }
                    } else {
                        offsetY = if (screenMotionProgress.value < 0.9f) {
                            screenMotionProgress.animateTo(0f)
                            windowState.value = PlayerWindowState.NORMAL
                            0f
                        } else {
                            screenMotionProgress.animateTo(1f)
                            1000f
                        }
                    }
                }
            ),
        factory = VideoPlayerBinding::inflate) {
        playerView1 = this.playerView
        this.playerView.player = exoPlayer
        this.root.progress = screenMotionProgress.value
        this.playerView.findViewById<IconicsImageView>(R.id.fullscreen).setOnClickListener {
            videoPlayerViewCallbacks?.goFullScreen()
        }
        this.composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                content()
            }
        }
    }
}


enum class VideoMode {
    NORMAL, FULLSCREEN
}