package com.imdmp.youtubecompose_ui.ui_player

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import com.google.android.exoplayer2.ExoPlayer
import com.imdmp.ui_core.theme.BlackDarkColor1
import com.imdmp.ui_core.theme.YoutubeComposeTheme
import com.imdmp.ui_core.theme.typography
import com.imdmp.youtubecompose_ui.R
import com.imdmp.youtubecompose_ui.ui_player.comments.CommentModel
import com.imdmp.youtubecompose_ui.ui_player.comments.CommentState
import com.imdmp.youtubecompose_ui.ui_player.comments.Comments
import com.imdmp.youtubecompose_ui.ui_player.controls.ControlState
import com.imdmp.youtubecompose_ui.ui_player.controls.Controls
import com.imdmp.youtubecompose_ui.ui_player.model.PlayerStatus
import com.imdmp.youtubecompose_ui.ui_player.model.VideoPlayerComposeScreenState
import com.imdmp.youtubecompose_ui.ui_player.model.VideoPlayerScreenCallbacks
import com.imdmp.youtubecompose_ui.ui_player.playback.Playback
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
        val (
            videoPlayer,
            pager,
            pagerTabs,
            controls,
            progressIndicator,
            titleBar,
            iconActionsBar,
            videoAuthorInfoBar,
            commentSeparatorLine,
            commentRow,
            comments,
        ) = createRefs()
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
            controlsCallback = videoPlayerScreenCallbacks,
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

        CommentHeaderRow(modifier = Modifier.constrainAs(commentRow) {
            top.linkTo(commentSeparatorLine.bottom, 8.dp)
        })

        Comments(
            listState = listState,
            modifier = Modifier
                .constrainAs(
                    comments
                ) {
                    top.linkTo(commentRow.bottom, 8.dp)
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
fun CommentHeaderRow(modifier: Modifier = Modifier) {

    Row(modifier = modifier.padding(start = 16.dp, end = 16.dp)) {
        Text(text = "Comments", style = typography.h3.copy(fontWeight = FontWeight.Normal))
        Spacer(modifier = Modifier.size(8.dp))
        Text("123", style = typography.subtitle1.copy(fontSize = 14.sp))
    }

}

@Composable
fun VideoAuthorInfoBar(modifier: Modifier = Modifier, state: VideoPlayerComposeScreenState) {
    ConstraintLayout(modifier = modifier.padding(start = 16.dp, end = 16.dp)) {
        val (profilePic, authorName, subs, subscribeButton) = createRefs()

        GlideImage(
            imageModel = state.authorUrl,
            previewPlaceholder = R.drawable.edsheeran,
            placeHolder = painterResource(id = R.drawable.edsheeran),
            error = painterResource(id = R.drawable.edsheeran),
            modifier = Modifier
                .clip(
                    CircleShape
                )
                .size(36.dp)
                .constrainAs(profilePic) {
                    start.linkTo(parent.start, 4.dp)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
        )

        Text(
            text = state.authorName,
            style = typography.h4,
            modifier = Modifier.constrainAs(authorName) {
                start.linkTo(profilePic.end, 8.dp)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            }
        )

        Text(text = "${state.likeCount}m subs",
            style = typography.subtitle2,
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
                tint = BlackDarkColor1,
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
        }

        Text(text = "23k",
            style = typography.button,
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
                tint = BlackDarkColor1,
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
        }

        Text(text = "Dislike",
            style = typography.button,

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
                tint = BlackDarkColor1,
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
        }

        Text(text = "Share",
            style = typography.button,

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
                tint = BlackDarkColor1,
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
        }

        Text(text = "Download",
            style = typography.button,
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
            style = typography.h2,
            modifier = Modifier.constrainAs(title) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
            }
        )


        Text(text = "${state.likeCount}k views . ",
            style = typography.subtitle2,
            modifier = Modifier.constrainAs(viewCount) {
                top.linkTo(title.bottom)
                start.linkTo(parent.start)
            })
        Text(text = "${state.datePosted}",
            style = typography.subtitle2,
            modifier = Modifier.constrainAs(uploadDate) {
                start.linkTo(viewCount.end, 2.dp)
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
    val commendModel2 = commentModel.copy(
        authorName = "RandomDude2",
        commentText = "Second"
    )

    val commendModel3 = commendModel2.copy(
        authorName = "RandomPerson3",
        commentText = "Third"
    )
    val videoPlayerScreenState = VideoPlayerComposeScreenState(
        commentList = listOf(commentModel, commendModel2, commendModel3),
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
