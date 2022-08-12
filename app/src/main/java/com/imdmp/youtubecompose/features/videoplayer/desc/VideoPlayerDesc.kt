package com.imdmp.youtubecompose.features.videoplayer.desc

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.imdmp.youtubecompose.R
import com.imdmp.youtubecompose.features.videoplayer.CommentModel
import com.imdmp.youtubecompose.features.videoplayer.CommentState
import com.imdmp.youtubecompose.features.videoplayer.Comments
import com.imdmp.ytcore.YTCoreTheme
import com.skydoves.landscapist.glide.GlideImage
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Regular
import compose.icons.fontawesomeicons.regular.ArrowAltCircleDown
import compose.icons.fontawesomeicons.regular.ShareSquare
import compose.icons.fontawesomeicons.regular.ThumbsDown
import compose.icons.fontawesomeicons.regular.ThumbsUp

@Composable
fun VideoPlayerDesc() {
    val state = VideoPlayerComposeScreenState.init()
    val listState = rememberLazyListState()

    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (videoPlayer, pager, pagerTabs, controls, progressIndicator, titleBar, iconActionsBar, videoAuthorInfoBar, commentSeparatorLine, comments) = createRefs()
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .constrainAs(videoPlayer) {
                top.linkTo(parent.top)
            })
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

    YTCoreTheme() {
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

@Preview
@Composable
fun PreviewVideoPlayerScreen() {

    val videoPlayerScreenState = getScreenStateForTest()

    YTCoreTheme {
        VideoPlayerDesc()
    }
}