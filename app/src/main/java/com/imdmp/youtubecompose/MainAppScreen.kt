package com.imdmp.youtubecompose

import SearchScreen
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.imdmp.youtubecompose.features.videolist.VideoListViewModel
import com.imdmp.youtubecompose.features.videolist.VideoListScreen
import com.imdmp.youtubecompose.features.navigation.model.Destination
import com.imdmp.youtubecompose.features.player.VideoPlayerScreen
import com.imdmp.youtubecompose.features.player.VideoPlayerViewModel
import com.imdmp.youtubecompose.features.search.SearchViewModel
import java.net.URLDecoder

@Composable
fun MainAppScreen(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Destination.Search.path
    ) {
        composable(Destination.VideoList.path) { backStackEntry ->
            val query = backStackEntry.arguments?.getString(Destination.VIDEO_LIST)

            val viewModel = hiltViewModel<VideoListViewModel>()
            VideoListScreen(viewModel, navController, URLDecoder.decode(query, "utf-8"))
        }

        composable(Destination.Player.path) { backStackEntry ->
            val streamUrl = backStackEntry.arguments?.getString(Destination.STREAM_URL)
            requireNotNull(streamUrl) { "streamUrl parameter wasn't found. Please make sure it's set!" }

            val videoPlayerViewModel = hiltViewModel<VideoPlayerViewModel>()
            VideoPlayerScreen(videoPlayerViewModel, URLDecoder.decode(streamUrl, "utf-8"))
        }

        composable(Destination.Search.path) {
            val viewModel = hiltViewModel<SearchViewModel>()
            SearchScreen(viewModel, navController)
        }
    }
}