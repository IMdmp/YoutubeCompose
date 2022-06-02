package com.imdmp.youtubecompose_ui.ui_player

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.calculateTargetValue
import androidx.compose.animation.core.tween
import androidx.compose.animation.splineBasedDecay
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.input.pointer.util.VelocityTracker
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.*
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.transition.TransitionSet
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
import com.imdmp.youtubecompose_ui.ui_player.model.PlayerStatus
import com.imdmp.youtubecompose_ui.ui_player.model.VideoPlayerComposeScreenState
import com.imdmp.youtubecompose_ui.ui_player.model.VideoPlayerScreenCallbacks
import com.imdmp.youtubecompose_ui.ui_player.model.WindowState
import com.imdmp.youtubecompose_ui.ui_player.playback.Playback
import com.skydoves.landscapist.glide.GlideImage
import compose.icons.FontAwesomeIcons
import compose.icons.Octicons
import compose.icons.fontawesomeicons.Regular
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.regular.ArrowAltCircleDown
import compose.icons.fontawesomeicons.regular.ShareSquare
import compose.icons.fontawesomeicons.regular.ThumbsDown
import compose.icons.fontawesomeicons.regular.ThumbsUp
import compose.icons.fontawesomeicons.solid.Pause
import compose.icons.octicons.X24
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue
import kotlin.math.roundToInt


const val VIDEO_PLAYER = "video_player"
const val PROGRESS_INDICATOR = "progress_indicator"
const val TITLE_BAR = "title_bar"
const val ICON_ACTION_BAR = "icon_actions_bar"
const val VIDEO_AUTHOR_INFO_BAR = "video_author_info_bar"
const val COMMENT_SEPARATOR_LINE = "comment_separator_line"
const val COMMENT_ROW = "comment_row"
const val COMMENTS = "comments"
const val SURFACE = "surface"
const val PAUSE_PLAY_BUTTON = "pause_play_button"
const val CLOSE_BUTTON = "close_button"
fun videoPlayerScreenConstraints(): ConstraintSet {

    return ConstraintSet {
        val videoPlayer = createRefFor(VIDEO_PLAYER)
        val progressIndicator = createRefFor(PROGRESS_INDICATOR)
        val titleBar = createRefFor(TITLE_BAR)
        val iconActionBar = createRefFor(ICON_ACTION_BAR)
        val videoAuthorInfoBar = createRefFor(VIDEO_AUTHOR_INFO_BAR)
        val commentSeparatorLine = createRefFor(COMMENT_SEPARATOR_LINE)
        val commentRow = createRefFor(COMMENT_ROW)
        val comments = createRefFor(COMMENTS)
        val surface = createRefFor(SURFACE)
        val closeButton = createRefFor(CLOSE_BUTTON)
        val pausePlayButton = createRefFor(PAUSE_PLAY_BUTTON)

        constrain(surface) {
            height = Dimension.matchParent
            width = Dimension.matchParent
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }

        constrain(videoPlayer) {
            start.linkTo(parent.start)
            top.linkTo(parent.top)
        }
        constrain(progressIndicator) {
            start.linkTo(videoPlayer.start)
            end.linkTo(videoPlayer.end)
            top.linkTo(videoPlayer.top)
            bottom.linkTo(videoPlayer.bottom)
        }

        constrain(titleBar) {
            top.linkTo(videoPlayer.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            width = Dimension.fillToConstraints
        }

        constrain(videoAuthorInfoBar) {
            top.linkTo(titleBar.bottom)
        }

        constrain(iconActionBar) {
            top.linkTo(videoAuthorInfoBar.bottom)
        }

        constrain(commentSeparatorLine) {
            top.linkTo(iconActionBar.bottom, 8.dp)

        }

        constrain(commentRow) {
            top.linkTo(commentSeparatorLine.bottom, 8.dp)
        }
        constrain(
            comments
        ) {
            top.linkTo(commentRow.bottom, 8.dp)
        }

        constrain(pausePlayButton) {
            visibility = Visibility.Gone
        }
        constrain(closeButton) {
            visibility = Visibility.Gone
        }
    }

}

fun collapseVideoPlayerScreenConstraints(): ConstraintSet {
    return ConstraintSet {
        val videoPlayer = createRefFor(VIDEO_PLAYER)
        val progressIndicator = createRefFor(PROGRESS_INDICATOR)
        val titleBar = createRefFor(TITLE_BAR)
        val iconActionBar = createRefFor(ICON_ACTION_BAR)
        val videoAuthorInfoBar = createRefFor(VIDEO_AUTHOR_INFO_BAR)
        val commentSeparatorLine = createRefFor(COMMENT_SEPARATOR_LINE)
        val commentRow = createRefFor(COMMENT_ROW)
        val comments = createRefFor(COMMENTS)
        val surface = createRefFor(SURFACE)
        val closeButton = createRefFor(CLOSE_BUTTON)
        val pausePlayButton = createRefFor(PAUSE_PLAY_BUTTON)

        constrain(surface) {
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            bottom.linkTo(parent.bottom)
            height = Dimension.preferredValue(100.dp)
            width = Dimension.matchParent

        }

        constrain(videoPlayer) {
            start.linkTo(parent.start)
            bottom.linkTo(parent.bottom)
            height = Dimension.preferredValue(100.dp)
        }

        constrain(titleBar) {
            start.linkTo(videoPlayer.end, 2.dp)
            end.linkTo(pausePlayButton.start, 2.dp)
            top.linkTo(surface.top)
            bottom.linkTo(surface.bottom)
            width = Dimension.fillToConstraints
        }

        constrain(pausePlayButton) {
            start.linkTo(titleBar.end)
            end.linkTo(closeButton.start)
            top.linkTo(surface.top)
            bottom.linkTo(surface.bottom)
            height = Dimension.preferredValue(16.dp)
            width = Dimension.preferredValue(16.dp)

        }

        constrain(closeButton) {
            start.linkTo(pausePlayButton.end)
            end.linkTo(parent.end)
            top.linkTo(surface.top)
            bottom.linkTo(surface.bottom)
            height = Dimension.preferredValue(16.dp)
            width = Dimension.preferredValue(16.dp)

        }

        constrain(progressIndicator) {
            visibility = Visibility.Gone
            bottom.linkTo(parent.bottom)
        }
        constrain(iconActionBar) {
            top.linkTo(videoAuthorInfoBar.bottom)
            visibility = Visibility.Gone
        }
        constrain(videoAuthorInfoBar) {
            top.linkTo(titleBar.bottom)
            visibility = Visibility.Gone
        }
        constrain(commentSeparatorLine) {
            top.linkTo(iconActionBar.bottom, 8.dp)
            visibility = Visibility.Gone
        }
        constrain(commentRow) {
            top.linkTo(commentSeparatorLine.bottom, 8.dp)
            visibility = Visibility.Gone
        }
        constrain(comments) {
            top.linkTo(commentRow.bottom, 8.dp)
            visibility = Visibility.Gone
        }


    }
}


@ExperimentalMotionApi
@OptIn(ExperimentalPagerApi::class)
@Composable
fun VideoPlayerScreen(
    modifier: Modifier = Modifier,
    player: ExoPlayer?,
    state: VideoPlayerComposeScreenState,
    videoPlayerScreenCallbacks: VideoPlayerScreenCallbacks,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    streamUrl: String
) {
    LaunchedEffect(key1 = Unit) {
        videoPlayerScreenCallbacks.prepareAndPlayVideoPlayer(streamUrl)
    }
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

    var offsetY by remember { mutableStateOf(0f) }
    val screenMotionProgress = remember {
        Animatable(0f)
    }
    val scope = rememberCoroutineScope()
    val windowState = remember { mutableStateOf(WindowState.NORMAL) }
    val draggableState = rememberDraggableState {
        offsetY += it
        scope.launch {
            screenMotionProgress.snapTo((offsetY / 1000).coerceAtLeast(0f).coerceAtMost(1f))

        }
    }
    val context = LocalContext.current
    val transition = remember {
        context.resources
            .openRawResource(R.raw.transition)
            .readBytes()
            .decodeToString()
    }

    MotionLayout(motionScene = MotionScene(content = transition), progress = screenMotionProgress.value) {
        Box(
            Modifier
                .layoutId(SURFACE)
                .background(Color.White)
                .fillMaxSize()
        ) {

        }
        Playback(
            Modifier
                .aspectRatio(16 / 9f)
                .layoutId(VIDEO_PLAYER)
                .clickable {
                    controlsVisible = !controlsVisible
                }
                .draggable(
                    orientation = Orientation.Vertical,
                    state = draggableState,
                    onDragStopped = {
                        if (windowState.value == WindowState.NORMAL) {
                            offsetY = if (screenMotionProgress.value > 0.1f) {
                                screenMotionProgress.animateTo(1f).endState.value
                                windowState.value = WindowState.COLLAPSED
                                1000f
                            } else {
                                screenMotionProgress.animateTo(0f)
                                0f
                            }
                        } else {
                            offsetY = if (screenMotionProgress.value < 0.9f) {
                                screenMotionProgress.animateTo(0f)
                                windowState.value = WindowState.NORMAL
                                0f
                            } else {
                                screenMotionProgress.animateTo(1f)
                                1000f
                            }
                        }
                    }
                )
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
                    .layoutId(PROGRESS_INDICATOR)
            )
        }


        if (windowState.value == WindowState.NORMAL) {
            TitleBar(
                modifier = Modifier
                    .padding(top = 8.dp, bottom = 8.dp)
                    .layoutId(TITLE_BAR), state = state
            )
        } else {
            Text(
                text = state.videoTitle,
                fontSize = 10.sp,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.layoutId(TITLE_BAR)
            )

            IconButton(
                modifier = modifier
                    .layoutId(PAUSE_PLAY_BUTTON)
                    .testTag(Tags.TAG_PAUSE_PLAY_BUTTON),
                onClick = { })
            {
                Icon(
                    tint = Color.Black,
                    imageVector = FontAwesomeIcons.Solid.Pause,
                    contentDescription = stringResource(R.string.pause)
                )
            }

            IconButton(
                modifier = modifier
                    .size(16.dp)
                    .layoutId(CLOSE_BUTTON),
                onClick = { })
            {
                Icon(
                    tint = Color.Black,
                    imageVector = Octicons.X24,
                    contentDescription = stringResource(R.string.close)
                )
            }
        }
        VideoAuthorInfoBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp, bottom = 4.dp)
                .layoutId(VIDEO_AUTHOR_INFO_BAR), state = state
        )
        IconActionsBar(
            modifier = Modifier
                .fillMaxWidth()
                .layoutId(ICON_ACTION_BAR), state = state
        )

        Box(
            modifier = Modifier
                .height(1.dp)
                .fillMaxWidth()
                .background(Color.LightGray)
                .layoutId(COMMENT_SEPARATOR_LINE)
        )

        CommentHeaderRow(modifier = Modifier.layoutId(COMMENT_ROW))

        Comments(
            listState = listState,
            modifier = Modifier
                .layoutId(COMMENTS),
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

fun Modifier.swipeToDismiss(
    onDismissed: () -> Unit
): Modifier = composed {
    val offsetX = remember { Animatable(0f) }
    pointerInput(Unit) {
        // Used to calculate fling decay.
        val decay = splineBasedDecay<Float>(this)
        // Use suspend functions for touch events and the Animatable.
        coroutineScope {
            while (true) {
                // Detect a touch down event.
                val pointerId = awaitPointerEventScope { awaitFirstDown().id }
                val velocityTracker = VelocityTracker()
                // Stop any ongoing animation.
                offsetX.stop()
                awaitPointerEventScope {
                    horizontalDrag(pointerId) { change ->
                        // Update the animation value with touch events.
                        launch {
                            offsetX.snapTo(
                                offsetX.value + change.positionChange().x
                            )
                        }
                        velocityTracker.addPosition(
                            change.uptimeMillis,
                            change.position
                        )
                    }
                }
                // No longer receiving touch events. Prepare the animation.
                val velocity = velocityTracker.calculateVelocity().x
                val targetOffsetX = decay.calculateTargetValue(
                    offsetX.value,
                    velocity
                )
                // The animation stops when it reaches the bounds.
                offsetX.updateBounds(
                    lowerBound = -size.width.toFloat(),
                    upperBound = size.width.toFloat()
                )
                launch {
                    if (targetOffsetX.absoluteValue <= size.width) {
                        // Not enough velocity; Slide back.
                        offsetX.animateTo(
                            targetValue = 0f,
                            initialVelocity = velocity
                        )
                    } else {
                        // The element was swiped away.
                        offsetX.animateDecay(velocity, decay)
                        onDismissed()
                    }
                }
            }
        }
    }
        .offset { IntOffset(offsetX.value.roundToInt(), 0) }
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


@OptIn(ExperimentalMotionApi::class)
@Preview
@Composable
fun PreviewVideoPlayerScreen() {

    val videoPlayerScreenState = getScreenStateForTest()

    YoutubeComposeTheme {
        VideoPlayerScreen(
            player = null,
            state = videoPlayerScreenState,
            videoPlayerScreenCallbacks = VideoPlayerScreenCallbacks.default(),
            streamUrl = ""
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
