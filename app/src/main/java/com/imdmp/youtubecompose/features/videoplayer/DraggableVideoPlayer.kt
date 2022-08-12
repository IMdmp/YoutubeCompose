package com.imdmp.youtubecompose.features.videoplayer

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidViewBinding
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.LifecycleOwner
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.imdmp.youtubecompose.R
import com.imdmp.youtubecompose.databinding.VideoPlayerBinding
import com.imdmp.youtubecompose.features.videoplayer.desc.VideoPlayerDesc
import com.mikepenz.iconics.view.IconicsImageView
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@Composable
fun DraggableVideoPlayer(
    modifier: Modifier = Modifier,
    videoPlayerViewModel: VideoPlayerViewModel,
    content: @Composable () -> Unit = { Surface {} },
    callback: (progress: Float) -> Unit = {},
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,

    ) {
    LaunchedEffect(videoPlayerViewModel.getState().currentStreamInfo) {
        if (videoPlayerViewModel.getState().currentStreamInfo.url.isNotEmpty()) {
            videoPlayerViewModel.initVideoPlayer(videoPlayerViewModel.getState().currentStreamInfo.url)
        }
    }

    DisposableEffect(key1 = videoPlayerViewModel) {
        videoPlayerViewModel.onStart()
        onDispose { videoPlayerViewModel.onStop() }
    }

    HandleLifecycleChanges(
        lifecycleOwner = lifecycleOwner,
        exoPlayer = videoPlayerViewModel.videoPlayer
    )

    DraggableVidPlayer(
        modifier = modifier,
        callback = callback,
        exoPlayer = videoPlayerViewModel.videoPlayer,
        videoPlayerViewCallbacks = videoPlayerViewModel,
        content = {
            Surface(modifier = it.fillMaxSize()) {
                VideoPlayerDesc(videoPlayerViewModel.getState())
            }
        }
    )
}

@Composable
fun DraggableVidPlayer(
    modifier: Modifier = Modifier,
    exoPlayer: ExoPlayer,
    videoPlayerViewCallbacks: VideoPlayerViewCallbacks? = null,
    callback: (progress: Float) -> Unit = {},
    content: @Composable (modifier: Modifier) -> Unit = { Surface {} }
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
            val result = (offsetY / 1000).coerceAtLeast(0f).coerceAtMost(1f)
            screenMotionProgress.snapTo(result)
        }
    }

    LaunchedEffect(key1 = screenMotionProgress.value) {
        snapshotFlow {
            screenMotionProgress
        }.collect {
            callback(it.value)
        }
    }

    LaunchedEffect(key1 = windowState.value) {
        playerView1?.useController = windowState.value != PlayerWindowState.COLLAPSED
    }
    ConstraintLayout {
        val (player, additionalContent) = createRefs()
        AndroidViewBinding(
            modifier = modifier
                .fillMaxWidth()
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
                )
                .constrainAs(player) {
                    top.linkTo(parent.top)
                },
            factory = VideoPlayerBinding::inflate
        ) {
            playerView1 = this.playerView
            this.playerView.player = exoPlayer
            this.root.progress = screenMotionProgress.value
            this.playerView.findViewById<IconicsImageView>(R.id.fullscreen).setOnClickListener {
                videoPlayerViewCallbacks?.goFullScreen()
            }

            this.closeButton.setOnClickListener {
                videoPlayerViewCallbacks?.closeButtonClicked()
            }
        }
        content(Modifier
            .constrainAs(additionalContent) {
                top.linkTo(parent.top, margin = 300.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                Dimension.Companion.fillToConstraints
            })

    }
}