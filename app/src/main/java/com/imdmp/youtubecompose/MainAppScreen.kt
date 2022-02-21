package com.imdmp.youtubecompose

import SearchScreen
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.imdmp.youtubecompose.features.fullscreenview.FullScreenView
import com.imdmp.youtubecompose.features.videolist.VideoListViewModel
import com.imdmp.youtubecompose.features.videolist.VideoListScreen
import com.imdmp.youtubecompose.features.navigation.model.Destination
import com.imdmp.youtubecompose.features.player.Playback
import com.imdmp.youtubecompose.features.player.VideoPlayerViewModel
import com.imdmp.youtubecompose.features.profile.ProfileScreen
import com.imdmp.youtubecompose.features.search.SearchViewModel
import com.imdmp.youtubecompose.features.settings.SettingsViewModel
import com.imdmp.youtubecompose.features.splash.SplashScreen
import timber.log.Timber
import java.net.URLDecoder

@Composable
fun MainAppScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(
    ),
    startDestination: String = Destination.Splash.path
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Destination.Splash.path) {

            SplashScreen(navController)
        }

        composable(Destination.VideoList.path) { backStackEntry ->
            val query = backStackEntry.arguments?.getString(Destination.VIDEO_LIST, "")

            val viewModel = hiltViewModel<VideoListViewModel>()
            if (query.isNullOrEmpty()) {
                VideoListScreen(viewModel, navController, "")
            } else {
                VideoListScreen(
                    query = URLDecoder.decode(query, "utf-8"),
                    videoListViewModel = viewModel,
                    navController = navController
                )
            }


        }

        composable(Destination.Player.path) { backStackEntry ->
            val streamUrl = backStackEntry.arguments?.getString(Destination.STREAM_URL)
            Timber.d("test stream url: $streamUrl")
            requireNotNull(streamUrl) { "streamUrl parameter wasn't found. Please make sure it's set!" }

            val videoPlayerViewModel = hiltViewModel<VideoPlayerViewModel>()
//            Playback(
//                streamUrl = URLDecoder.decode(streamUrl, "utf-8"),
//                player = videoPlayerViewModel.player,
//                videoPlayerScreenCallbacks = videoPlayerViewModel
//            )
        }

        composable(Destination.Search.path) {
            val viewModel = hiltViewModel<SearchViewModel>()
            SearchScreen(viewModel, navController)
        }

        composable(Destination.Profile.path) {
            val settingsViewModel = hiltViewModel<SettingsViewModel>()

            ProfileScreen(settingsViewModel)
        }

        composable(Destination.FullScreenView.path) {

            FullScreenView()
        }

        composable(Destination.Test.path){
            Text("")
        }
    }
}