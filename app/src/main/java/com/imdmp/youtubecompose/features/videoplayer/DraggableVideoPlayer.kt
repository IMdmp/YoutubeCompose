package com.imdmp.youtubecompose.features.videoplayer

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLifecycleOwner
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
    var savedPlayerView: StyledPlayerView? = remember { null }
    var visible by remember { mutableStateOf(true) }
    var yOffset by remember { mutableStateOf(0f) }
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
            visible = it.value < 0.7f
            yOffset = it.value * 1000
            callback(it.value)
        }
    }

    LaunchedEffect(key1 = windowState.value) {
        savedPlayerView?.useController = windowState.value != PlayerWindowState.COLLAPSED
    }

    val topPadding = remember { Animatable(0f) }
    val videoDetailsAnimationScope = rememberCoroutineScope()
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
            videoDetailsAnimationScope.launch { topPadding.animateTo((this@AndroidViewBinding.playerView.height).toFloat()) }

            this.playerView.apply {
                this.player = exoPlayer
                savedPlayerView = this
                findViewById<IconicsImageView>(R.id.fullscreen).setOnClickListener {
                    videoPlayerViewCallbacks?.goFullScreen()
                }
            }
            this.root.progress = screenMotionProgress.value

            this.closeButton.setOnClickListener {
                videoPlayerViewCallbacks?.closeButtonClicked()
            }

        }

        AnimatedVisibility(
            visible = visible,
            exit = fadeOut(
                // Overwrites the default animation with tween
                animationSpec = tween(durationMillis = 50)
            )
        ) {
            // Content that needs to appear/disappear goes here:
            content(
                Modifier
                    .padding(top = LocalDensity.current.run { topPadding.value.toDp() })
                    .offset(y = LocalDensity.current.run { yOffset.toDp() })
                    .constrainAs(additionalContent) {
                        top.linkTo(player.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        Dimension.Companion.fillToConstraints
                    })
        }


    }
}
