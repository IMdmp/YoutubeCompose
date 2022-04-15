package com.imdmp.youtubecompose.features.videolist

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.imdmp.youtubecompose.features.videolist.model.VideoListViewModel

@Composable
fun VideoListScreen(
    videoListViewModel: VideoListViewModel = hiltViewModel(),
    navController: NavController,
) {
//    val videoListState = videoListViewModel.videoList.observeAsState().value
//
//    LaunchedEffect(videoListViewModel.query) {
//        videoListViewModel.search(videoListViewModel.query)
//    }

//    VideoListScreen(videoListState, object : com.imdmp.ui_home.VideoListScreenActions {
//        override fun videoItemSelected(videoListItem: com.imdmp.ui_home.VideoListItem) {
//            navController.navigate(Destination.Player.createRoute(videoListItem.streamUrl))
//        }
//
//        override fun searchClicked() {
//            navController.navigate(Destination.Search.path)
//        }
//
//        override fun profileClicked() {
//            navController.navigate(Destination.Profile.path)
//        }
//    })
}

