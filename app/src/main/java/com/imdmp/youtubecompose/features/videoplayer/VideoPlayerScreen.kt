package com.imdmp.youtubecompose.features.videoplayer

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import com.google.android.exoplayer2.ExoPlayer
import com.imdmp.youtubecompose.base.Tags
import com.imdmp.youtubecompose.features.ui.theme.YoutubeComposeTheme
import com.imdmp.youtubecompose.features.videoplayer.controls.Controls
import com.imdmp.youtubecompose.features.videoplayer.controls.ControlsCallback
import com.imdmp.youtubecompose.features.videoplayer.model.PlayerStatus
import com.imdmp.youtubecompose.features.videoplayer.model.VideoPlayerScreenCallbacks
import com.imdmp.youtubecompose.features.videoplayer.model.VideoPlayerScreenState
import com.imdmp.youtubecompose.features.videoplayer.playback.Playback

@OptIn(ExperimentalPagerApi::class)
@Composable
fun VideoPlayerScreen(
    player: ExoPlayer?,
    videoPlayerScreenState: VideoPlayerScreenState,
    videoPlayerScreenCallbacks: VideoPlayerScreenCallbacks,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    controlsCallback: ControlsCallback,
) {

    val pagerState = rememberPagerState()

    HandleLifecycleChanges(
        lifecycleOwner = lifecycleOwner,
        state = videoPlayerScreenState.playerStatus,
        exoPlayer = player
    )

    var controlsVisible by remember {
        mutableStateOf(
            false
        )
    }

    val alphaAnimation by animateFloatAsState(
        targetValue = if (controlsVisible) 0.7f else 0f,
        animationSpec = if (controlsVisible) {
            tween(delayMillis = 0)
        } else tween(delayMillis = 750)
    )
    LaunchedEffect(key1 = Unit) {
        videoPlayerScreenCallbacks.retrieveComments()
    }


    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (videoPlayer, pager, pagerTabs, controls) = createRefs()
        Playback(
            Modifier
                .aspectRatio(16 / 9f)
                .constrainAs(videoPlayer) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
                .clickable {
                    controlsVisible = !controlsVisible
                }
                .testTag(Tags.TEST),
            player = player,
            playerScreenCallbacks = videoPlayerScreenCallbacks,
        )

        Controls(
            controlsCallback = controlsCallback,
            modifier = Modifier
                .alpha(alphaAnimation)
                .constrainAs(controls) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(videoPlayer.bottom)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                })



        HorizontalPager(
            count = 2, state = pagerState, modifier = Modifier
                .constrainAs(pager) {
                    top.linkTo(videoPlayer.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(pagerTabs.top)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                }
        ) {
            if (this.currentPage == 0) {
//                Comments(videoPlayerScreenState = videoPlayerScreenState)
            } else {
                Text("test here.")
            }
        }

        Surface(
            color = MaterialTheme.colors.background,
            modifier = Modifier
                .size(30.dp)
                .constrainAs(pagerTabs) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(pager.bottom)
                    width = Dimension.fillToConstraints
                },
        ) {
            TabRow(
                selectedTabIndex = pagerState.currentPage,
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator()
                    Modifier.pagerTabIndicatorOffset(pagerState, tabPositions)
                }
            ) {

                Tab(
                    text = { Text("Title1") },
                    selectedContentColor = Color.Blue,
                    unselectedContentColor = Color.Black,
                    selected = pagerState.currentPage == 0,
                    onClick = {}
                )
                Tab(
                    text = { Text("Title2") },
                    unselectedContentColor = Color.Black,
                    selected = pagerState.currentPage == 1,
                    onClick = {}
                )
            }
        }
    }
}

@Composable
fun HandleLifecycleChanges(
    lifecycleOwner: LifecycleOwner,
    state: PlayerStatus,
    exoPlayer: ExoPlayer?
) {
    Box(
        modifier = Modifier
            .testTag(Tags.TAG_PLAYER_LIFECYCLER_HANDLER)
            .fillMaxSize()
    ) {

    }
    if (exoPlayer == null)
        return
    val currentPlayerStatus by rememberUpdatedState(state)

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (currentPlayerStatus == PlayerStatus.PLAYING) {
                if (event == Lifecycle.Event.ON_RESUME) {
                    exoPlayer.play()
                } else if (event == Lifecycle.Event.ON_START) {
                    exoPlayer.pause()
                }
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
}

//@Preview
//@Composable
//fun VideoDescription() {
//
//}

@Preview
@Composable
fun PreviewVideoPlayerScreen() {
    val videoPlayerScreenState = VideoPlayerScreenState(
        commentList = listOf("coment1")
    )

    YoutubeComposeTheme {
        VideoPlayerScreen(
            player = null,
            videoPlayerScreenState = videoPlayerScreenState,
            videoPlayerScreenCallbacks = VideoPlayerScreenCallbacks.default(),
            controlsCallback = ControlsCallback.default()
        )
    }
}

