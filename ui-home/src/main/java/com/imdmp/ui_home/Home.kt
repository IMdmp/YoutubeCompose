package com.imdmp.ui_home

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import com.imdmp.ui_home.topappbar.HomeTopAppBar

@Composable
fun HomeScreen() {

}

@Composable
private fun VideoListScreen(
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