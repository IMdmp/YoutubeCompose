package com.imdmp.youtubecompose.features.videolist

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
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

@Preview
@Composable
fun PreviewVideoListScreen() {

//    val sampleVideoListItem = com.imdmp.ui_home.VideoListItem(
//        imageUrl = "", title = "", author = "", authorImageUrl = null, viewCount = 0, streamUrl = ""
//
//    )
//
//    val videoList = listOf(
//        sampleVideoListItem.copy(title = "Title1", author = "Author1", viewCount = 2L),
//        sampleVideoListItem.copy(title = "Title2", author = "Author2", viewCount = 1L),
//        sampleVideoListItem.copy(title = "Title3", author = "Author3", viewCount = 5L),
//        sampleVideoListItem.copy(title = "Title4", author = "Author4", viewCount = 10L)
//
//    )
//    YoutubeComposeTheme {
//        VideoListScreen(
//            videoList = videoList,
//            videoListScreenActions = com.imdmp.ui_home.VideoListScreenActions.default()
//        )
//    }
}