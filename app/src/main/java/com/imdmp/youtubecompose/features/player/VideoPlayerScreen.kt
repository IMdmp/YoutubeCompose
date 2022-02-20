package com.imdmp.youtubecompose.features.player

import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.ui.PlayerView
import com.imdmp.youtubecompose.base.Tags
import com.imdmp.youtubecompose.features.navigation.model.Destination

@Composable
fun Playback(
    player: ExoPlayer,
    streamUrl: String,
    navController: NavController,
    videoPlayerScreenCallbacks: VideoPlayerScreenCallbacks,
) {
    MaterialTheme {
        val context = LocalContext.current
        LaunchedEffect(player) {
            videoPlayerScreenCallbacks.prepareAndPlayVideoPlayer(streamUrl)
        }

        DisposableEffect(
            AndroidView(
                modifier = Modifier.fillMaxSize(),
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
                videoPlayerScreenCallbacks.disposeVideoPlayer()
            }
        }

        Button(onClick = {
            navController.navigate(Destination.FullScreenView.path)

        }, Modifier.testTag(Tags.TAG_BUTTON_SET_FULLSCREENVIEW)) {
            Text("Full Screen")
        }
    }
}


//@OptIn(ExperimentalPagerApi::class)
//@Composable
//fun Playback(
//    streamUrl: String,
//    videoPlayerScreenCallbacks: VideoPlayerScreenCallbacks,
//    player: ExoPlayer
//) {
//    val context = LocalContext.current
//
//    Playback()
//}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Playback() {
    val pagerState = rememberPagerState()

    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (videoPlayer, pager, pagerTabs) = createRefs()
//        Playback(
//            Modifier
//                .aspectRatio(16 / 9f)
//                .constrainAs(videoPlayer) {
//                    top.linkTo(parent.top)
//                    start.linkTo(parent.start)
//                })


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
            Text("test here.")
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

@Preview
@Composable
fun PreviewVideoPlayerScreen() {
    val videoPlayerUrl = ""
//    VideoPlayerScreen(streamUrl = videoPlayerUrl, VideoPlayerScreenCallbacks.default())

}

