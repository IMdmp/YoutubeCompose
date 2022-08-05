package com.imdmp.youtubecompose

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import com.imdmp.youtubecompose.features.videolist.VideoListScreen
import com.imdmp.youtubecompose.features.videolist.VideoListViewModel
import com.imdmp.youtubecompose.features.videoplayer.DraggableVideoPlayer
import com.imdmp.youtubecompose.features.videoplayer.VideoPlayerViewModel

@Composable
fun MainScreen(
) {
    val videoListViewModel = hiltViewModel<VideoListViewModel>()
    val mainViewModel = hiltViewModel<MainViewModel>()
    val videoPlayerViewModel = hiltViewModel<VideoPlayerViewModel>()
    val mainScreenState = mainViewModel.mainScreenState


    VideoListScreen(videoListViewModel = videoListViewModel)
    if (mainScreenState.value == MainState.PLAYER) {

        DraggableVideoPlayer(
            modifier = Modifier
                .zIndex(3f),
            videoPlayerViewModel = videoPlayerViewModel
        )
    }
}