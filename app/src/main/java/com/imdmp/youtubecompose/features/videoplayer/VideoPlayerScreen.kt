package com.imdmp.youtubecompose.features.videoplayer

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.testTag
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
import com.imdmp.youtubecompose.R
import com.imdmp.youtubecompose.base.Tags
import com.imdmp.youtubecompose.features.ui.theme.YoutubeComposeTheme
import com.imdmp.youtubecompose.features.videoplayer.comments.CommentModel
import com.imdmp.youtubecompose.features.videoplayer.comments.CommentState
import com.imdmp.youtubecompose.features.videoplayer.comments.Comments
import com.imdmp.youtubecompose.features.videoplayer.controls.ControlState
import com.imdmp.youtubecompose.features.videoplayer.controls.Controls
import com.imdmp.youtubecompose.features.videoplayer.controls.ControlsCallback
import com.imdmp.youtubecompose.features.videoplayer.model.PlayerStatus
import com.imdmp.youtubecompose.features.videoplayer.model.VideoPlayerScreenCallbacks
import com.imdmp.youtubecompose.features.videoplayer.model.VideoPlayerScreenState
import com.imdmp.youtubecompose.features.videoplayer.playback.Playback
import com.skydoves.landscapist.glide.GlideImage
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.ArrowDown
import compose.icons.fontawesomeicons.solid.Share
import compose.icons.fontawesomeicons.solid.ThumbsDown
import compose.icons.fontawesomeicons.solid.ThumbsUp

@OptIn(ExperimentalPagerApi::class)
@Composable
fun VideoPlayerScreen(
    player: ExoPlayer?,
    state: VideoPlayerScreenState,
    videoPlayerScreenCallbacks: VideoPlayerScreenCallbacks,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    controlsCallback: ControlsCallback,

    ) {

    val pagerState = rememberPagerState()

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
        val (videoPlayer, pager, pagerTabs, controls, progressIndicator, titleBar, iconActionsBar, videoAuthorInfoBar, comments) = createRefs()
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


        TitleBar(modifier = Modifier.constrainAs(titleBar) {
            top.linkTo(videoPlayer.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            width = Dimension.fillToConstraints
        }, state = state)
        IconActionsBar(modifier = Modifier.constrainAs(iconActionsBar) {
            top.linkTo(titleBar.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            width = Dimension.fillToConstraints
        }, state = state)
        VideoAuthorInfoBar(modifier = Modifier.constrainAs(videoAuthorInfoBar) {
            top.linkTo(iconActionsBar.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            width = Dimension.fillToConstraints
        }, state = state)

        Comments(
            modifier = Modifier.constrainAs(
                comments
            ) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
                top.linkTo(videoAuthorInfoBar.bottom, 4.dp)
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
fun VideoAuthorInfoBar(modifier: Modifier = Modifier, state: VideoPlayerScreenState) {

    ConstraintLayout(modifier = modifier.padding(16.dp)) {
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
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                }
        )

        Text(
            text = state.authorName,
            fontSize = 16.sp,
            modifier = modifier.constrainAs(authorName) {
                top.linkTo(parent.top)
                start.linkTo(profilePic.end)
            },
        )

        Text(
            text = "${state.numberOfSubs}M subscribers",
            modifier = Modifier.constrainAs(subs) {
                start.linkTo(profilePic.end, 16.dp)
                top.linkTo(authorName.bottom, 4.dp)
                bottom.linkTo(parent.bottom)
            }
        )

        Text(
            text = "SUBSCRIBED",
            fontSize = 32.sp,
            modifier = Modifier.constrainAs(subscribeButton) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                end.linkTo(parent.end)
            })
    }
}

@Composable
fun IconActionsBar(modifier: Modifier = Modifier, state: VideoPlayerScreenState) {
    Row(modifier = modifier, horizontalArrangement = Arrangement.SpaceEvenly) {
        VideoScreenActionIcon(
            iconDesc = "${state.likeCount} k",
            icon = FontAwesomeIcons.Solid.ThumbsUp
        )

        VideoScreenActionIcon(
            iconDesc = "Dislike",
            icon = FontAwesomeIcons.Solid.ThumbsDown
        )

        VideoScreenActionIcon(
            iconDesc = "Share",
            icon = FontAwesomeIcons.Solid.Share
        )

        VideoScreenActionIcon(
            iconDesc = "Download",
            icon = FontAwesomeIcons.Solid.ArrowDown
        )
    }
}

@Composable
fun VideoScreenActionIcon(modifier: Modifier = Modifier, iconDesc: String, icon: ImageVector) {
    Column(modifier = modifier) {
        IconButton(onClick = { /*TODO*/ }) {
            Icon(imageVector = icon, contentDescription = null, Modifier.size(16.dp))
        }

        Text(text = iconDesc)
    }
}

@Composable
fun TitleBar(modifier: Modifier = Modifier, state: VideoPlayerScreenState) {

    ConstraintLayout(modifier = modifier.padding(16.dp)) {
        val (title, viewCount, uploadDate) = createRefs()

        Text(
            text = state.videoTitle,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
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

    val commentModel = CommentModel(
        authorName = "RandomDude",
        commentText = "First.",
        profilePic = "",
        likeCount = 97,
        timeCommented = "9 hours ago"

    )
    val videoPlayerScreenState = VideoPlayerScreenState(
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

    YoutubeComposeTheme {
        VideoPlayerScreen(
            player = null,
            state = videoPlayerScreenState,
            videoPlayerScreenCallbacks = VideoPlayerScreenCallbacks.default(),
            controlsCallback = ControlsCallback.default()
        )
    }
}

