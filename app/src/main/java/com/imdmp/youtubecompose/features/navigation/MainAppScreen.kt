package com.imdmp.youtubecompose.features.navigation

import SearchScreen
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.imdmp.youtubecompose.features.home.DataItem
import com.imdmp.youtubecompose.features.home.HomeListViewModel
import com.imdmp.youtubecompose.features.home.VideoListScreen
import com.imdmp.youtubecompose.features.navigation.model.Destination
import com.imdmp.youtubecompose.features.player.VideoPlayerScreen
import com.imdmp.youtubecompose.features.player.VideoPlayerViewModel
import timber.log.Timber
import java.net.URLDecoder

@Composable
fun MainAppScreen(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Destination.Home.path
    ) {
        composable(Destination.Home.path) {
            val viewModel = hiltViewModel<HomeListViewModel>()
            VideoListScreen(viewModel, navController)
        }

        composable(Destination.Player.path) { backStackEntry ->
            val streamUrl = backStackEntry.arguments?.getString(Destination.STREAM_URL)
            requireNotNull(streamUrl) { "streamUrl parameter wasn't found. Please make sure it's set!" }

            val videoPlayerViewModel = hiltViewModel<VideoPlayerViewModel>()
            VideoPlayerScreen(videoPlayerViewModel, URLDecoder.decode(streamUrl,"utf-8"))
        }

        composable(Destination.Search.path) {
            SearchScreen()
        }
    }
}