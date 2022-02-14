package com.imdmp.youtubecompose.features.player

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

@Composable
fun VideoPlayerScreen(videoPlayerViewModel: VideoPlayerViewModel, streamUrl: String) {

    MaterialTheme {
        VideoPlayerScreen(streamUrl, videoPlayerViewModel)
    }
}


@OptIn(ExperimentalPagerApi::class)
@Composable
private fun VideoPlayerScreen(
    streamUrl: String,
    videoPlayerScreenCallbacks: VideoPlayerScreenCallbacks
) {
    val context = LocalContext.current
    val player = getVideoPlayer(context)
    val playerView = PlayerView(context)

    val playWhenReady by rememberSaveable {
        mutableStateOf(true)
    }
    playerView.player = player
    LaunchedEffect(Unit) {
        this.launch(Dispatchers.IO) {
            try {
                Timber.d("test: stream url: $streamUrl")
                val mediaSource = videoPlayerScreenCallbacks.getMediaSource(streamUrl)
                withContext(Dispatchers.Main) {
                    player.setMediaSource(mediaSource)
                    player.prepare()
                    Timber.d("test: play when ready: ${player.playWhenReady}")
                    player.playWhenReady = playWhenReady
                    player.play()
                }


            } catch (e: java.lang.Exception) {
                e.printStackTrace()
                Timber.d("ERRRO!")
            }
        }
    }
    val pagerState = rememberPagerState()

    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (videoPlayer, pager, pagerTabs) = createRefs()

        Box(
            Modifier
                .aspectRatio(16 / 9f)
                .constrainAs(videoPlayer) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }) {

            AndroidView(
                factory = {
                    playerView
                })
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


private fun getVideoPlayer(context: Context): ExoPlayer {
    return SimpleExoPlayer.Builder(context).build().apply {
        addListener(object : Player.Listener {
            override fun onPlaybackStateChanged(state: Int) {
                super.onPlaybackStateChanged(state)
                Timber.d("state changed.")
                if (state == Player.STATE_READY) {
//                        videoPlayerViewModel.handleEvent(VideoEvent.VideoLoaded)
                } else if (state == Player.EVENT_PLAYER_ERROR) {
//                        videoPlayerViewModel.handleEvent(VideoEvent.VideoError)
                }
            }
        })
    }
}

@Preview
@Composable
fun PreviewVideoPlayerScreen() {
    val videoPlayerUrl = ""
    VideoPlayerScreen(streamUrl = videoPlayerUrl, VideoPlayerScreenCallbacks.default())

}

