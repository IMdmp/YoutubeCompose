package com.imdmp.youtubecompose

import SearchScreen
import android.content.pm.ActivityInfo
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.imdmp.youtubecompose.features.fullscreenview.FullScreenView
import com.imdmp.youtubecompose.features.videolist.VideoListViewModel
import com.imdmp.youtubecompose.features.videolist.VideoListScreen
import com.imdmp.youtubecompose.features.navigation.model.Destination
import com.imdmp.youtubecompose.features.player.VideoPlayerScreen
import com.imdmp.youtubecompose.features.player.VideoPlayerScreenState
import com.imdmp.youtubecompose.features.player.VideoPlayerViewModel
import com.imdmp.youtubecompose.features.profile.ProfileScreen
import com.imdmp.youtubecompose.features.search.SearchViewModel
import com.imdmp.youtubecompose.features.settings.SettingsViewModel
import com.imdmp.youtubecompose.features.splash.SplashScreen
import kotlinx.coroutines.flow.observeOn
import timber.log.Timber
import java.net.URLDecoder

@Composable
fun MainAppScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(
    ),
    startDestination: String = Destination.Splash.path,
    baseActivityCallbacks: BaseActivityCallbacks? = null
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

            videoPlayerViewModel.updateUrl(streamUrl)
            VideoPlayerScreen(
                navController = navController,
                player = videoPlayerViewModel.player,
                videoPlayerScreenState = videoPlayerViewModel.uiState.collectAsState().value,
                videoPlayerScreenCallbacks = videoPlayerViewModel,
                lifecycleOwner = LocalLifecycleOwner.current
            )
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
            baseActivityCallbacks?.setOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
            val parentEntry = remember {
                navController.getBackStackEntry(Destination.Player.path)
            }

            val videoPlayerViewModel = hiltViewModel<VideoPlayerViewModel>(parentEntry)

            FullScreenView(
                navController = navController,
                videoPlayerViewModel = videoPlayerViewModel,
                player = videoPlayerViewModel.player,
                playerScreenCallbacks = videoPlayerViewModel,
                )
        }

        composable(Destination.Test.path) {
            Text("")
        }
    }
}