package com.imdmp.youtubecompose

import android.content.ClipData
import androidx.compose.foundation.Image
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun ListScreen(){
    val dataItem = DataItem("https://random.dog/ea60dc85-258d-471b-8299-3fdb1c9e8319.jpg",title = "Top 10 Facts","Lemmino")
    Column {
        VideoItem(item = dataItem)
    }
}

@Composable
fun VideoItem(item: DataItem){
    ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
        val (image,authorImage,title,button,subtitle) = createRefs()

        GlideImage(
            imageModel = item.imageUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(200.dp)
                .constrainAs(image) {
                    linkTo(
                        start = parent.start,
                        end = parent.end,
                    )
                    top.linkTo(parent.top)
                    width = Dimension.fillToConstraints
                }
        )
        Image(
            painter = painterResource(id = R.drawable.p3),
            contentDescription = null,
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .constrainAs(authorImage) {
                    start.linkTo(parent.start, margin = 12.dp)
                    top.linkTo(image.bottom, margin = 16.dp)
                    end.linkTo(title.start)
                }
        )
        Text(
            text = item.title,
            style = MaterialTheme.typography.h6.copy(fontSize = 14.sp),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.constrainAs(title) {
                linkTo(
                    start = authorImage.end,
                    startMargin = 16.dp,
                    end = button.start,
                    endMargin = 16.dp
                )
                linkTo(
                    top = authorImage.top,
                    bottom = subtitle.top
                )
                width = Dimension.fillToConstraints
            }
        )
        Text(
            text = "${item.author} . ${item.viewCount}k views . 6 hours ago",
            style = MaterialTheme.typography.subtitle2,
            modifier = Modifier
                .constrainAs(subtitle) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(title.start)
                    width = Dimension.fillToConstraints
                }
                .padding(bottom = 24.dp)
        )
        IconButton(
            onClick = { },
            modifier = Modifier
                .constrainAs(button) {
                    top.linkTo(image.bottom)
                    end.linkTo(parent.end)
                }
        ) {
            Icon(Icons.Default.MoreVert, tint = Color.Gray, contentDescription = null)
        }
    }
}

@Composable
@Preview
fun PreviewVideoItem(){
    val dataItem = DataItem("",title = "Top 10 Facts","Lemmino")
    VideoItem(dataItem)
}

data class DataItem(
    val imageUrl:String,
    val title :String = "",
    val author:String ="",
    val viewCount:Int = 0
)

//@Composable
//@Preview
//fun PreviewListScreen(){
//    ListScreen()
//}