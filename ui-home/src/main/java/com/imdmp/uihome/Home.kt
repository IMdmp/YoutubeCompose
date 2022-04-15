package com.imdmp.uihome

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.imdmp.ui_core.theme.YoutubeComposeTheme
import com.imdmp.uihome.topappbar.HomeTopAppBar
import com.imdmp.uihome.videoitem.VideoItem

@Composable
fun HomeScreen() {

}

@Composable
fun VideoListScreen(
    videoList: List<VideoListItem>?,
    videoListScreenActions: VideoListScreenActions,
) {
    Scaffold(
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

    val sampleVideoListItem = VideoListItem(
        imageUrl = "", title = "", author = "", authorImageUrl = null, viewCount = 0, streamUrl = ""

    )

    val videoList = listOf(
        sampleVideoListItem.copy(title = "Title1", author = "Author1", viewCount = 2L),
        sampleVideoListItem.copy(title = "Title2", author = "Author2", viewCount = 1L),
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