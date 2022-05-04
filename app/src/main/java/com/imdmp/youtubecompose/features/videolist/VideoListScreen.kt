package com.imdmp.youtubecompose.features.videolist

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.imdmp.uihome.VideoListItem
import com.imdmp.uihome.VideoListScreen
import com.imdmp.uihome.VideoListScreenActions
import com.imdmp.youtubecompose.base.ui.navigation.model.Destination
import com.imdmp.youtubecompose.features.videolist.model.VideoListViewModel

@Composable
fun HomeScreen(
    videoListViewModel: VideoListViewModel = hiltViewModel(),
    navController: NavController,
) {
    LaunchedEffect(key1 = videoListViewModel.query.value) {
        videoListViewModel.retrieveVideoList()
    }
    VideoListScreen(videoListViewModel.videoList.value, object : VideoListScreenActions {
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

