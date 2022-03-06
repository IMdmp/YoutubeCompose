package com.imdmp.youtubecompose.features.videoplayer.comments

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.imdmp.youtubecompose.R
import com.imdmp.youtubecompose.base.Tags
import com.imdmp.youtubecompose.base.ui.theme.YoutubeComposeTheme
import com.skydoves.landscapist.glide.GlideImage
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.ThumbsUp

@Composable
fun Comments(
    listState : LazyListState = rememberLazyListState(),
    modifier: Modifier = Modifier, commentState: CommentState) {
    LazyColumn(
        state = listState,
        modifier = modifier
            .fillMaxWidth()
            .testTag(Tags.TAG_COMMENTS_LIST)
    ) {
        items(commentState.commentModelList) {
            Comment(commentModel = it)
        }
    }
}

@Composable
private fun Comment(modifier: Modifier = Modifier, commentModel: CommentModel) {
    ConstraintLayout(modifier = modifier.fillMaxWidth()) {
        val (profilePic, authorName, commentText, likeCount, timeCommented, likeIcon) = createRefs()

        GlideImage(imageModel = commentModel.profilePic,
            previewPlaceholder = R.drawable.eminem,
            modifier = Modifier
                .size(24.dp)
                .clip(CircleShape)
                .constrainAs(profilePic) {
                    start.linkTo(parent.start, 8.dp)
                    top.linkTo(parent.top, 8.dp)
                })

        Text(
            text = commentModel.authorName,
            modifier = Modifier
                .constrainAs(authorName) {
                    top.linkTo(profilePic.top)
                    start.linkTo(profilePic.end, 8.dp)
                }
        )

        Text(
            text = commentModel.commentText,
            maxLines = 2,
            modifier = Modifier
                .constrainAs(commentText) {
                    top.linkTo(authorName.bottom, 16.dp)
                    start.linkTo(profilePic.end, 8.dp)
                }
        )

        Icon(
            imageVector = FontAwesomeIcons.Solid.ThumbsUp,
            contentDescription = null,
            modifier = Modifier
                .size(16.dp)
                .constrainAs(likeIcon) {
                    start.linkTo(commentText.start)
                    top.linkTo(commentText.bottom, 8.dp)
                }
        )

        Text(text = "${commentModel.likeCount} K",
            modifier = Modifier
                .constrainAs(likeCount) {
                    start.linkTo(likeIcon.end, 8.dp)
                    top.linkTo(commentText.bottom, 8.dp)
                })

        Text(
            text = "${commentModel.timeCommented}",
            modifier = Modifier.constrainAs(timeCommented) {
                start.linkTo(likeCount.end, 16.dp)
                top.linkTo(commentText.bottom, 8.dp)
            }
        )
    }

}

@Preview
@Composable
fun PreviewComment() {

    YoutubeComposeTheme {
        Comment(
            commentModel = CommentModel(
                authorName = "Adam Something",
                commentText = "Efficient trains solves a lot of city problems",
                profilePic = "",
                likeCount = 2,
                timeCommented = "8 hours ago"
            )
        )
    }
}


@Preview
@Composable
fun PreviewComments() {
    val comment1 = CommentModel(
        authorName = "",
        commentText = "",
        profilePic = "",
        likeCount = 0,
        timeCommented = ""
    )

    val comment2 = CommentModel(
        authorName = "",
        commentText = "",
        profilePic = "",
        likeCount = 0,
        timeCommented = ""
    )

    val commentState = CommentState(
        commentModelList = listOf(comment1, comment2),
        isLoading = false,
        error = null
    )

    YoutubeComposeTheme {
        Comments(commentState = commentState)
    }
}