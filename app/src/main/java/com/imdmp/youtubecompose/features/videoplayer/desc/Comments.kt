package com.imdmp.youtubecompose.features.videoplayer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.imdmp.youtubecompose.R
import com.imdmp.youtubecompose.base.TestTags
import com.imdmp.youtubecompose.features.videoplayer.desc.CommentModel
import com.imdmp.youtubecompose.features.videoplayer.desc.CommentState
import com.imdmp.ytcore.YTCoreTheme
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
            .testTag(TestTags.TAG_COMMENTS_LIST)
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
                .size(32.dp)
                .clip(CircleShape)
                .constrainAs(profilePic) {
                    start.linkTo(parent.start, 8.dp)
                    top.linkTo(parent.top, 8.dp)
                })

        Text(
            text = commentModel.authorName,
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier
                .constrainAs(authorName) {
                    top.linkTo(parent.top, 2.dp)
                    start.linkTo(profilePic.end, 8.dp)
                }
        )

        Text(
            text = commentModel.commentText,
            maxLines = 3,
            style = MaterialTheme.typography.body2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .constrainAs(commentText) {
                    top.linkTo(authorName.bottom, 6.dp)
                    start.linkTo(profilePic.end, 8.dp)
                    end.linkTo(parent.end, 8.dp)
                    width = Dimension.fillToConstraints
                }
        )

        Icon(
            imageVector = FontAwesomeIcons.Regular.ThumbsUp,
            contentDescription = null,
            modifier = Modifier
                .size(16.dp)
                .constrainAs(likeIcon) {
                    start.linkTo(commentText.start)
                    top.linkTo(commentText.bottom, 8.dp)
                }
        )

        Text(text = "${commentModel.likeCount} K",
            style = MaterialTheme.typography.subtitle1,
            fontSize = 14.sp,
            modifier = Modifier
                .constrainAs(likeCount) {
                    start.linkTo(likeIcon.end, 4.dp)
                    top.linkTo(commentText.bottom, 8.dp)
                })

        Text(
            text = "${commentModel.timeCommented}",
            style = MaterialTheme.typography.subtitle1,
            fontSize = 14.sp,
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

    YTCoreTheme {
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

    YTCoreTheme() {
        Comments(commentState = commentState)
    }
}