package com.imdmp.youtubecompose_ui.uihome

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.MotionLayout
import com.imdmp.ui_core.theme.YoutubeComposeTheme
import com.imdmp.youtubecompose_ui.uihome.topappbar.HomeTopAppBar
import com.imdmp.youtubecompose_ui.uihome.videoitem.VideoItem

@Composable
fun HomeScreen() {
    val sampleVideoListItem = VideoListItem.default()

    val videoList = listOf(
        sampleVideoListItem.copy(
            title = "Long title but probably only one line long",
            author = "Author1",
            viewCount = 2L
        ),
        sampleVideoListItem.copy(
            title = "This is a sample of a long title that can probably take two lines long",
            author = "Author2",
            viewCount = 1L
        ),
        sampleVideoListItem.copy(title = "Title3", author = "Author3", viewCount = 5L),
        sampleVideoListItem.copy(title = "Title4", author = "Author4", viewCount = 10L)

    )



//    
//    MotionLayout(motionScene = , progress = ) {
//        VideoListScreen(
//            videoList = videoList,
//            videoListScreenActions = VideoListScreenActions.default()
//        )
//    }


}

@Composable
fun VideoListScreen(
    modifier: Modifier = Modifier,
    videoList: List<VideoListItem>?,
    videoListScreenActions: VideoListScreenActions,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            HomeTopAppBar(
                toolbarActions = videoListScreenActions
            )
        }
    ) {
        if (videoList.isNullOrEmpty()) {
            LoadingScreen()
        } else {
            LazyColumn {
                items(videoList) { data ->
                    VideoItem(item = data, videoListScreenActions)
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewVideoListScreen() {

    val sampleVideoListItem = VideoListItem.default()

    val videoList = listOf(
        sampleVideoListItem.copy(
            title = "Long title but probably only one line long",
            author = "Author1",
            viewCount = 2L
        ),
        sampleVideoListItem.copy(
            title = "This is a sample of a long title that can probably take two lines long",
            author = "Author2",
            viewCount = 1L
        ),
        sampleVideoListItem.copy(title = "Title3", author = "Author3", viewCount = 5L),
        sampleVideoListItem.copy(title = "Title4", author = "Author4", viewCount = 10L)

    )
    YoutubeComposeTheme {
        VideoListScreen(
            videoList = videoList,
            videoListScreenActions = VideoListScreenActions.default()
        )
    }
}
