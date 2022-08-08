package com.imdmp.youtubecompose

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.imdmp.youtubecompose.features.navigation.model.Destination
import com.imdmp.youtubecompose.features.profile.ProfileScreen
import com.imdmp.youtubecompose.features.splash.SplashScreen
import com.imdmp.youtubecompose.features.videolist.VideoListScreen
import com.imdmp.youtubecompose.features.videolist.VideoListViewModel

@Composable
fun MainNavScreen(
    navController: NavHostController = rememberNavController(),
    startDestination: String = Destination.VideoList.path,
) {
    val videoListViewModel = hiltViewModel<VideoListViewModel>()

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Destination.Splash.path) {
            SplashScreen()
        }

        composable(Destination.VideoList.path) {
            VideoListScreen(videoListViewModel = videoListViewModel)
        }

        composable(Destination.Search.path) {
            //TODO: same path for now.
            VideoListScreen(videoListViewModel = videoListViewModel)
        }

        composable(Destination.Profile.path) {
            ProfileScreen()
        }
    }
}