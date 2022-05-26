package com.imdmp.youtubecompose.features.videolist

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.imdmp.youtubecompose.base.ui.navigation.model.Destination
import com.imdmp.youtubecompose.features.videolist.model.VideoListViewModel
import com.imdmp.youtubecompose.features.videoplayer.VideoPlayerViewModel
import com.imdmp.youtubecompose_ui.ui_player.VideoPlayerScreen
import com.imdmp.youtubecompose_ui.uihome.VideoListItem
import com.imdmp.youtubecompose_ui.uihome.VideoListScreen
import com.imdmp.youtubecompose_ui.uihome.VideoListScreenActions

@Composable
fun HomeScreen(
    videoListViewModel: VideoListViewModel = hiltViewModel(),
    navController: NavController,
) {
    val videoScreen = remember { mutableStateOf(false) }
    val streamUrl = remember { mutableStateOf("") }
    LaunchedEffect(key1 = videoListViewModel.query.value) {
        videoListViewModel.retrieveVideoList()
    }
    VideoListScreen(
        videoList = videoListViewModel.videoList.value,
        videoListScreenActions = object : VideoListScreenActions {
            override fun videoItemSelected(videoListItem: VideoListItem) {
//                navController.navigate(Destination.Player.createRoute(videoListItem.streamUrl))
                streamUrl.value = videoListItem.streamUrl
                videoScreen.value = true
            }

            override fun searchClicked() {
                navController.navigate(Destination.Search.path)
            }

            override fun profileClicked() {
                navController.navigate(Destination.Profile.path)
            }
        })

    if (videoScreen.value) {
        OpenVideoScreen(streamUrl = streamUrl.value, videoScreen)
    }

}

@OptIn(ExperimentalMotionApi::class)
@Composable
fun OpenVideoScreen(streamUrl: String, videoScreen: MutableState<Boolean>) {
    val videoPlayerViewModel = hiltViewModel<VideoPlayerViewModel>()

    VideoPlayerScreen(
        player = videoPlayerViewModel.player,
        state = videoPlayerViewModel.uiState.collectAsState().value,
        videoPlayerScreenCallbacks = videoPlayerViewModel,
        lifecycleOwner = LocalLifecycleOwner.current,
        streamUrl = streamUrl
    ) {
        videoScreen.value = false
    }
}

