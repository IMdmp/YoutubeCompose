package com.imdmp.uihome.videoitem

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.imdmp.uihome.R
import com.imdmp.uihome.VideoItemActions
import com.imdmp.uihome.VideoListItem
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun VideoItem(item: VideoListItem, videoItemActions: VideoItemActions) {
    Surface(
        contentColor = Color.Black
    ) {
        ConstraintLayout(modifier = Modifier
            .fillMaxWidth()
            .clickable {
                videoItemActions.videoItemSelected(item)
            }) {
            val (videoThumbnail, authorImage, videoTitle, moreOptionsButton, views, timeCreated, authorName) = createRefs()

            GlideImage(
                imageModel = item.imageUrl,
                contentDescription = null,
                previewPlaceholder = R.drawable.food11,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(200.dp)
                    .constrainAs(videoThumbnail) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                        width = Dimension.fillToConstraints
                    }
            )
            GlideImage(
                imageModel = item.authorImageUrl ?: "",
                contentDescription = null,
                previewPlaceholder = R.drawable.eminem,
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .constrainAs(authorImage) {
                        start.linkTo(parent.start, margin = 12.dp)
                        top.linkTo(videoThumbnail.bottom, margin = 16.dp)
                        end.linkTo(videoTitle.start)
                    }
            )
            Text(
                text = item.title,
                style = MaterialTheme.typography.h4.copy(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                ),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.constrainAs(videoTitle) {
                    start.linkTo(authorImage.end, 16.dp)
                    end.linkTo(moreOptionsButton.start, 16.dp)
                    top.linkTo(videoThumbnail.bottom, 12.dp)
                    width = Dimension.fillToConstraints
                }
            )
            Text(
                text = "${item.author} . ",
                style = MaterialTheme.typography.subtitle2,
                modifier = Modifier
                    .constrainAs(authorName) {
                        top.linkTo(videoTitle.bottom)
                        start.linkTo(videoTitle.start)
                        end.linkTo(views.start)
                    }
            )

            Text(
                text = "${item.viewCount}m views . 6 hours ago",
                style = MaterialTheme.typography.subtitle2,
                modifier = Modifier
                    .constrainAs(views) {
                        top.linkTo(videoTitle.bottom)
                        start.linkTo(authorName.end)
                        end.linkTo(parent.end, 16.dp)
                        width = Dimension.fillToConstraints
                    }
                    .padding(bottom = 24.dp)
            )
            IconButton(
                onClick = { },
                modifier = Modifier
                    .constrainAs(moreOptionsButton) {
                        top.linkTo(videoTitle.top)
                        end.linkTo(parent.end)
                    }
            ) {
                Icon(Icons.Default.MoreVert, tint = Color.Black, contentDescription = null)
            }
        }

    }
}

@Composable
@Preview
fun PreviewVideoItem() {
    val dataItem = com.imdmp.uihome.VideoListItem.default().copy(
        title = "Awesome Kurzgesagt Vid",
        author = "Kurzgesagt",
        viewCount = 10
    )
    VideoItem(dataItem, VideoItemActions.default())
}
