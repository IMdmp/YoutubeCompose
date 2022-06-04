package com.imdmp.youtubecompose.features.videolist

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.imdmp.youtubecompose.MainScreenCallback
import com.imdmp.youtubecompose.base.ui.navigation.model.Destination
import com.imdmp.youtubecompose.features.videolist.model.VideoListViewModel
import com.imdmp.youtubecompose_ui.uihome.VideoListItem
import com.imdmp.youtubecompose_ui.uihome.VideoListScreen
import com.imdmp.youtubecompose_ui.uihome.VideoListScreenActions

@Composable
fun HomeScreen(
    videoListViewModel: VideoListViewModel = hiltViewModel(),
    navController: NavController,
    mainScreenCallback: MainScreenCallback
) {

    LaunchedEffect(key1 = videoListViewModel.query.value) {
        videoListViewModel.retrieveVideoList()
    }
    VideoListScreen(
        videoList = videoListViewModel.videoList.value,
        videoListScreenActions = object : VideoListScreenActions {
            override fun videoItemSelected(videoListItem: VideoListItem) {
                mainScreenCallback.openVideoScreen(videoListItem.streamUrl)
            }

            override fun searchClicked() {
                navController.navigate(Destination.Search.path)
            }

            override fun profileClicked() {
                navController.navigate(Destination.Profile.path)
            }
        })
}
