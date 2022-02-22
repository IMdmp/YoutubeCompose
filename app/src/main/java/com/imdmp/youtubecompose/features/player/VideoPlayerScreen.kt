package com.imdmp.youtubecompose.features.player

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import com.google.android.exoplayer2.ExoPlayer

@OptIn(ExperimentalPagerApi::class)
@Composable
fun VideoPlayerScreen(
    navController: NavController,
    player: ExoPlayer,
    videoPlayerScreenState: VideoPlayerScreenState,
    videoPlayerScreenCallbacks: VideoPlayerScreenCallbacks,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
) {

    val pagerState = rememberPagerState()
    HandleLifecycleChanges(
        lifecycleOwner = lifecycleOwner,
        state = videoPlayerScreenState.playerStatus,
        exoPlayer = player
    )

    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (videoPlayer, pager, pagerTabs) = createRefs()
        Playback(
            Modifier
                .aspectRatio(16 / 9f)
                .constrainAs(videoPlayer) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                },
            player = player,
            streamUrl = videoPlayerScreenState.streamUrl,
            navController = navController,
            playerScreenCallbacks = videoPlayerScreenCallbacks,
        )

        LaunchedEffect(key1 = Unit ){
            videoPlayerScreenCallbacks.retrieveComments()
        }
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
                LazyColumn() {
                    items(videoPlayerScreenState.commentList) {
                        Text(text = it)
                    }
                }


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
    exoPlayer: ExoPlayer
) {
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

@Preview
@Composable
fun PreviewVideoPlayerScreen() {
    val videoPlayerUrl = ""
//    VideoPlayerScreen(streamUrl = videoPlayerUrl, VideoPlayerScreenCallbacks.default())

}

