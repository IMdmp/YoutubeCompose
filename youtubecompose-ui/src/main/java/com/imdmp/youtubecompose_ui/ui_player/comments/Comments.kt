package com.imdmp.youtubecompose_ui.ui_player.comments

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.imdmp.ui_core.R
import com.imdmp.ui_core.theme.YoutubeComposeTheme
import com.imdmp.ui_core.theme.typography
import com.imdmp.youtubecompose_ui.ui_player.Tags
import com.skydoves.landscapist.glide.GlideImage
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Regular
import compose.icons.fontawesomeicons.regular.ThumbsUp


@Composable
fun Comments(
    modifier: Modifier = Modifier, listState: LazyListState = rememberLazyListState(),
    commentState: CommentState
) {

    LazyColumn(
        state = listState,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier
            .fillMaxWidth()
            .testTag(Tags.TAG_COMMENTS_LIST)
    ) {

        commentState.commentModelList.forEach { comment ->
            item {
                Comment(commentModel = comment)
            }
            item {
                LineSeparator()
            }
        }
    }
}

@Composable
private fun Comment(modifier: Modifier = Modifier, commentModel: CommentModel) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp)
    ) {
        val (profilePic, authorName, commentText, likeCount, timeCommented, likeIcon) = createRefs()

        GlideImage(imageModel = commentModel.profilePic,
            previewPlaceholder = R.drawable.eminem,
            error = painterResource(id = R.drawable.eminem),
            modifier = Modifier
                .size(24.dp)
                .clip(CircleShape)
                .constrainAs(profilePic) {
                    start.linkTo(parent.start, 8.dp)
                    top.linkTo(parent.top, 8.dp)
                })

        Text(
            text = commentModel.authorName,
            style = typography.subtitle1,
            modifier = Modifier
                .constrainAs(authorName) {
                    top.linkTo(parent.top, 2.dp)
                    start.linkTo(profilePic.end, 8.dp)
                }
        )

        Text(
            text = " . ${commentModel.timeCommented}",
            style = typography.subtitle1,
            modifier = Modifier.constrainAs(timeCommented) {
                start.linkTo(authorName.end)
                top.linkTo(parent.top, 2.dp)
            }
        )

        Text(
            text = commentModel.commentText,
            maxLines = 3,
            style = typography.body2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .constrainAs(commentText) {
                    top.linkTo(authorName.bottom, 2.dp)
                    start.linkTo(profilePic.end, 8.dp)
                    end.linkTo(parent.end, 8.dp)
                    width = Dimension.fillToConstraints
                }
        )

        Icon(
            imageVector = FontAwesomeIcons.Regular.ThumbsUp,
            contentDescription = null,
            modifier = Modifier
                .size(12.dp)
                .constrainAs(likeIcon) {
                    start.linkTo(commentText.start)
                    top.linkTo(commentText.bottom, 16.dp)
                }
        )

        Text(text = "${commentModel.likeCount} K",
            style = typography.subtitle1,
            fontSize = 14.sp,
            modifier = Modifier
                .constrainAs(likeCount) {
                    start.linkTo(likeIcon.end, 4.dp)
                    top.linkTo(commentText.bottom, 12.dp)
                })

    }

}

@Preview
@Composable
fun PreviewComment() {

    YoutubeComposeTheme {
        Comment(
            commentModel = CommentModel(
                authorName = "Adam Something",
                commentText = "Efficient trains solves a lot of city problems. " +
                        "Alohamora wand elf parchment, Wingardium Leviosa hippogriff," +
                        "house dementors betrayal. Holly, Snape centaur portkey ghost Hermione spell bezoar Scabbers. Peruvian-Night-Powder werewolf, Dobby pear-tickle half-moon-glasses, Knight-Bus",
                profilePic = "",
                likeCount = 2,
                timeCommented = "8 hours ago"
            )
        )
    }
}


@Composable
fun LineSeparator(modifier: Modifier = Modifier) {

    Box(
        modifier = modifier
            .height(1.dp)
            .fillMaxWidth()
            .background(Color.LightGray)
    )
}

@Preview
@Composable
fun PreviewComments() {
    val comment1 = CommentModel(
        authorName = "Author Something",
        commentText = "Comment Text",
        profilePic = "",
        likeCount = 10,
        timeCommented = ""
    )

    val comment2 = CommentModel(
        authorName = "Author something",
        commentText = "Comment text that is very long and probably going to take more than two lines to fit in this thing.",
        profilePic = "",
        likeCount = 12,
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
