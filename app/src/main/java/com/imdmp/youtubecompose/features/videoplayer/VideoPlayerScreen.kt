package com.imdmp.youtubecompose.features.videoplayer

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
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
import com.google.accompanist.pager.rememberPagerState
import com.google.android.exoplayer2.ExoPlayer
import com.imdmp.youtubecompose.R
import com.imdmp.youtubecompose.base.Tags
import com.imdmp.youtubecompose.base.ui.theme.YoutubeComposeTheme
import com.imdmp.youtubecompose.features.videoplayer.comments.CommentModel
import com.imdmp.youtubecompose.features.videoplayer.comments.CommentState
import com.imdmp.youtubecompose.features.videoplayer.comments.Comments
import com.imdmp.youtubecompose.features.videoplayer.controls.ControlState
import com.imdmp.youtubecompose.features.videoplayer.controls.Controls
import com.imdmp.youtubecompose.features.videoplayer.controls.ControlsCallback
import com.imdmp.youtubecompose.features.videoplayer.model.PlayerStatus
import com.imdmp.youtubecompose.features.videoplayer.model.VideoPlayerScreenCallbacks
import com.imdmp.youtubecompose.features.videoplayer.model.VideoPlayerComposeScreenState
import com.imdmp.youtubecompose.features.videoplayer.playback.Playback
import com.skydoves.landscapist.glide.GlideImage
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Regular
import compose.icons.fontawesomeicons.regular.ArrowAltCircleDown
import compose.icons.fontawesomeicons.regular.ShareSquare
import compose.icons.fontawesomeicons.regular.ThumbsDown
import compose.icons.fontawesomeicons.regular.ThumbsUp

@OptIn(ExperimentalPagerApi::class)
@Composable
fun VideoPlayerScreen(
    player: ExoPlayer?,
    state: VideoPlayerComposeScreenState,
    videoPlayerScreenCallbacks: VideoPlayerScreenCallbacks,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    controlsCallback: ControlsCallback,

    ) {

    val pagerState = rememberPagerState()
    val listState = rememberLazyListState()
    HandleLifecycleChanges(
        lifecycleOwner = lifecycleOwner,
        state = state.playerStatus,
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
        val (videoPlayer, pager, pagerTabs, controls, progressIndicator, titleBar, iconActionsBar, videoAuthorInfoBar, commentSeparatorLine,comments) = createRefs()
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
            onUpdate = { playerView ->
                when (state.playerStatus) {
                    PlayerStatus.PAUSED -> {
                        playerView.player?.pause()
                    }
                    PlayerStatus.PLAYING -> {
                        playerView.player?.play()
                    }
                }
            }
        )

        if (state.playerStatus == PlayerStatus.LOADING) {
            CircularProgressIndicator(
                color = Color.Blue,
                modifier = Modifier
                    .constrainAs(progressIndicator) {
                        start.linkTo(videoPlayer.start)
                        end.linkTo(videoPlayer.end)
                        top.linkTo(videoPlayer.top)
                        bottom.linkTo(videoPlayer.bottom)
                    })
        }

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
                },
            controlState = if (state.playerStatus == PlayerStatus.PAUSED) {
                ControlState.PAUSED
            } else {
                ControlState.PLAYING
            }
        )


        TitleBar(
            modifier = Modifier
                .padding(top = 8.dp, bottom = 8.dp)
                .constrainAs(titleBar) {
                    top.linkTo(videoPlayer.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }, state = state
        )
        VideoAuthorInfoBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp, bottom = 4.dp)
                .constrainAs(videoAuthorInfoBar) {
                    top.linkTo(titleBar.bottom)
                }, state = state
        )
        IconActionsBar(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(iconActionsBar) {
                    top.linkTo(videoAuthorInfoBar.bottom)
                }, state = state
        )

        Box(
            modifier = Modifier
                .height(1.dp)
                .fillMaxWidth()
                .background(Color.LightGray)
                .constrainAs(commentSeparatorLine) {
                    top.linkTo(iconActionsBar.bottom, 8.dp)

                }
        )

        Comments(
            listState = listState,
            modifier = Modifier
                .constrainAs(
                comments
            ) {
                top.linkTo(commentSeparatorLine.bottom, 4.dp)
            },
            commentState = CommentState(
                commentModelList = state.commentList,
                isLoading = false,
                error = null
            ),
        )


//        HorizontalPager(
//            count = 2, state = pagerState, modifier = Modifier
//                .constrainAs(pager) {
//                    top.linkTo(videoPlayer.bottom)
//                    start.linkTo(parent.start)
//                    end.linkTo(parent.end)
//                    bottom.linkTo(pagerTabs.top)
//                    width = Dimension.fillToConstraints
//                    height = Dimension.fillToConstraints
//                }
//        ) {
//            if (this.currentPage == 0) {
//                Comments(
//                    commentState = CommentState(
//                        commentModelList = state.commentList,
//                        isLoading = false,
//                        error = null
//
//                    ),
//                )
//            } else {
//                Text("test here.")
//            }
//        }
//
//        Surface(
//            color = MaterialTheme.colors.background,
//            modifier = Modifier
//                .size(30.dp)
//                .constrainAs(pagerTabs) {
//                    bottom.linkTo(parent.bottom)
//                    start.linkTo(parent.start)
//                    end.linkTo(parent.end)
//                    top.linkTo(pager.bottom)
//                    width = Dimension.fillToConstraints
//                },
//        ) {
//            TabRow(
//                selectedTabIndex = pagerState.currentPage,
//                indicator = { tabPositions ->
//                    TabRowDefaults.Indicator()
//                    Modifier.pagerTabIndicatorOffset(pagerState, tabPositions)
//                }
//            ) {
//
//                Tab(
//                    text = { Text("Title1") },
//                    selectedContentColor = Color.Blue,
//                    unselectedContentColor = Color.Black,
//                    selected = pagerState.currentPage == 0,
//                    onClick = {}
//                )
//                Tab(
//                    text = { Text("Title2") },
//                    unselectedContentColor = Color.Black,
//                    selected = pagerState.currentPage == 1,
//                    onClick = {}
//                )
//            }
//        }
    }
}

@Composable
fun VideoAuthorInfoBar(modifier: Modifier = Modifier, state: VideoPlayerComposeScreenState) {
    ConstraintLayout(modifier = modifier) {
        val (profilePic, authorName, subs, subscribeButton) = createRefs()

        GlideImage(
            imageModel = state.authorUrl,
            previewPlaceholder = R.drawable.edsheeran,
            modifier = Modifier
                .clip(
                    CircleShape
                )
                .size(24.dp)
                .constrainAs(profilePic) {
                    start.linkTo(parent.start, 4.dp)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
        )

        Text(
            text = state.authorName,
            style = MaterialTheme.typography.h2,
            modifier = Modifier.constrainAs(authorName) {
                start.linkTo(profilePic.end, 8.dp)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            }
        )

        Text(text = "${state.likeCount}m subs",
            modifier = Modifier.constrainAs(subs) {
                top.linkTo(parent.top, 16.dp)
                end.linkTo(parent.end, 8.dp)
                bottom.linkTo(parent.bottom, 16.dp)
            }
        )


    }
}


@Preview
@Composable
fun PreviewVideoAuthorInfoBar() {
    val videoPlayerScreenState = getScreenStateForTest()

    YoutubeComposeTheme {
        VideoAuthorInfoBar(modifier = Modifier.fillMaxWidth(), state = videoPlayerScreenState)
    }
}

@Composable
fun IconActionsBar(modifier: Modifier = Modifier, state: VideoPlayerComposeScreenState) {
    ConstraintLayout(
        modifier = modifier,
    ) {
        val (thumbsUp, thumbsDown, share, download) = createRefs()
        val (thumbsUpLabel, thumbsDownLabel, shareLabel, downloadLabel) = createRefs()

        IconButton(onClick = { /*TODO*/ },
            modifier = Modifier.constrainAs(thumbsUp) {
                start.linkTo(parent.start)
                end.linkTo(thumbsDown.start)
                top.linkTo(parent.top)
            }
        ) {
            Icon(
                imageVector = FontAwesomeIcons.Regular.ThumbsUp,
                contentDescription = null,
                Modifier.size(24.dp)
            )
        }

        Text(text = "23k",
            modifier = Modifier.constrainAs(thumbsUpLabel) {
                start.linkTo(thumbsUp.start)
                end.linkTo(thumbsUp.end)
                top.linkTo(thumbsUp.bottom)
            }
        )

        IconButton(onClick = { /*TODO*/ },
            modifier = Modifier.constrainAs(thumbsDown) {
                start.linkTo(thumbsUp.end)
                end.linkTo(share.start)
                top.linkTo(parent.top)
            }) {
            Icon(
                imageVector = FontAwesomeIcons.Regular.ThumbsDown,
                contentDescription = null,
                Modifier.size(24.dp)
            )
        }

        Text(text = "Dislike",
            modifier = Modifier.constrainAs(thumbsDownLabel) {
                start.linkTo(thumbsDown.start)
                end.linkTo(thumbsDown.end)
                top.linkTo(thumbsDown.bottom)
            }
        )

        IconButton(onClick = { /*TODO*/ },
            modifier = Modifier.constrainAs(share) {
                start.linkTo(thumbsDown.end)
                end.linkTo(download.start)
                top.linkTo(parent.top)
            }) {
            Icon(
                imageVector = FontAwesomeIcons.Regular.ShareSquare,
                contentDescription = null,
                Modifier.size(24.dp)
            )
        }

        Text(text = "Share",
            modifier = Modifier.constrainAs(shareLabel) {
                start.linkTo(share.start)
                end.linkTo(share.end)
                top.linkTo(share.bottom)
            }
        )

        IconButton(onClick = { /*TODO*/ },
            modifier = Modifier.constrainAs(download) {
                start.linkTo(share.end)
                end.linkTo(parent.end)
                top.linkTo(parent.top)
            }) {
            Icon(
                imageVector = FontAwesomeIcons.Regular.ArrowAltCircleDown,
                contentDescription = null,
                Modifier.size(24.dp)
            )
        }

        Text(text = "Download",
            modifier = Modifier.constrainAs(downloadLabel) {
                start.linkTo(download.start)
                end.linkTo(download.end)
                top.linkTo(download.bottom)
            }
        )

    }
}

//@Composable
//fun VideoScreenActionIcon(modifier: Modifier = Modifier, iconDesc: String, icon: ImageVector) {
//    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
//        IconButton(onClick = { /*TODO*/ }) {
//            Icon(imageVector = icon, contentDescription = null, Modifier.size(24.dp))
//        }
//
//        Text(text = iconDesc)
//    }
//}

@Composable
fun TitleBar(modifier: Modifier = Modifier, state: VideoPlayerComposeScreenState) {

    ConstraintLayout(modifier = modifier.padding(start = 16.dp, end = 16.dp)) {
        val (title, viewCount, uploadDate) = createRefs()

        Text(
            text = state.videoTitle,
            style = MaterialTheme.typography.h1,
            modifier = Modifier.constrainAs(title) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
            }
        )


        Text(text = "${state.likeCount}k views . ",
            modifier = Modifier.constrainAs(viewCount) {
                top.linkTo(title.bottom)
                start.linkTo(parent.start)
            })
        Text(text = "${state.datePosted}",
            modifier = Modifier.constrainAs(uploadDate) {
                start.linkTo(viewCount.end, 16.dp)
                top.linkTo(title.bottom)
            })
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
            .size(5.dp)
            .testTag(Tags.TAG_PLAYER_LIFECYCLER_HANDLER)
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

    val videoPlayerScreenState = getScreenStateForTest()

    YoutubeComposeTheme {
        VideoPlayerScreen(
            player = null,
            state = videoPlayerScreenState,
            videoPlayerScreenCallbacks = VideoPlayerScreenCallbacks.default(),
            controlsCallback = ControlsCallback.default()
        )
    }
}

private fun getScreenStateForTest(): VideoPlayerComposeScreenState {
    val commentModel = CommentModel(
        authorName = "RandomDude",
        commentText = "First.",
        profilePic = "",
        likeCount = 97,
        timeCommented = "9 hours ago"

    )
    val videoPlayerScreenState = VideoPlayerComposeScreenState(
        commentList = listOf(commentModel),
        playerStatus = PlayerStatus.IDLE,
        streamUrl = "",
        videoTitle = "Sweet and Savory Babka",
        views = 865,
        datePosted = "2 weeks ago",
        likeCount = 55,
        authorUrl = "",
        authorName = "Binging with Babish",
        numberOfSubs = 1,
        videoDescription = "This is a sample video description",
    )

    return videoPlayerScreenState
}