package com.imdmp.youtubecompose

import SearchCombinerScreen
import android.content.pm.ActivityInfo
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.imdmp.youtubecompose.base.ui.navigation.model.Destination
import com.imdmp.youtubecompose.features.fullscreenview.FullScreenView
import com.imdmp.youtubecompose.features.profile.ProfileScreen
import com.imdmp.youtubecompose.features.splash.SplashScreen
import com.imdmp.youtubecompose.features.videolist.HomeScreen
import com.imdmp.youtubecompose.features.videolist.model.VideoListViewModel
import com.imdmp.youtubecompose.features.videoplayer.VideoPlayerViewModel
import com.imdmp.youtubecompose_ui.ui_player.VideoPlayerScreen
import timber.log.Timber
import java.net.URLDecoder

@ExperimentalMaterialApi
@Composable
fun MainAppScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = Destination.Splash.path,
    baseActivityCallbacks: BaseActivityCallbacks? = null
) {
    val systemUiController = rememberSystemUiController()
    val useDarkIcons = MaterialTheme.colors.isLight

    SideEffect {
        systemUiController.setStatusBarColor(
            color = Color.Transparent,
            darkIcons = useDarkIcons
        )
    }

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
            val videoListViewModel = hiltViewModel<VideoListViewModel>()
            videoListViewModel.updateCurrentQuery(URLDecoder.decode(query, "utf-8"))

            if (query.isNullOrEmpty()) {
                HomeScreen(navController = navController)
            } else {
                HomeScreen(
                    videoListViewModel = videoListViewModel,
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
                player = videoPlayerViewModel.player,
                state = videoPlayerViewModel.uiState.collectAsState().value,
                videoPlayerScreenCallbacks = videoPlayerViewModel,
                lifecycleOwner = LocalLifecycleOwner.current,
            )
        }

        composable(Destination.Search.path) {
            SearchCombinerScreen(navController = navController)
        }

        composable(Destination.Profile.path) {
            ProfileScreen()
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
