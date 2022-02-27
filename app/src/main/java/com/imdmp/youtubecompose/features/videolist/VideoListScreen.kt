package com.imdmp.youtubecompose.features.videolist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.imdmp.youtubecompose.features.ui.navigation.model.Destination
import com.imdmp.youtubecompose.features.ui.theme.YoutubeComposeTheme
import com.imdmp.youtubecompose.features.videolist.model.VideoListItem
import com.imdmp.youtubecompose.features.videolist.model.VideoListScreenActions
import com.imdmp.youtubecompose.features.videolist.model.VideoListViewModel
import com.imdmp.youtubecompose.features.videolist.topappbar.HomeTopAppBar
import com.imdmp.youtubecompose.features.videolist.videoitem.VideoItem

@Composable
fun VideoListScreen(
    videoListViewModel: VideoListViewModel = hiltViewModel(),
    navController: NavController,
) {
    val videoListState = videoListViewModel.videoList.observeAsState().value

    LaunchedEffect(videoListViewModel.query) {
        videoListViewModel.search(videoListViewModel.query)
    }

    VideoListScreen(videoListState, navController, object : VideoListScreenActions {
        override fun videoItemSelected(videoListItem: VideoListItem) {
            navController.navigate(Destination.Player.createRoute(videoListItem.streamUrl))
        }

        override fun searchClicked() {
            navController.navigate(Destination.Search.path)
        }

        override fun profileClicked() {
            navController.navigate(Destination.Profile.path)
        }
    })
}

@Composable
private fun VideoListScreen(
    videoList: List<VideoListItem>?,
    navController: NavController,
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

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(
            color = Color.Blue
        )
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
            navController = rememberNavController(),
            videoListScreenActions = VideoListScreenActions.default()
        )
    }
}