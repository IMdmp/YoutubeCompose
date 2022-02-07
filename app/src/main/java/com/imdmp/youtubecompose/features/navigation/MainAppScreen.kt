package com.imdmp.youtubecompose.features.navigation

import SearchScreen
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.imdmp.youtubecompose.features.home.HomeListViewModel
import com.imdmp.youtubecompose.features.home.VideoListScreen
import com.imdmp.youtubecompose.features.navigation.model.Destination

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

        composable(Destination.Player.path) {
//            VideoPlayerScreen(, dataItem =)
        }

        composable(Destination.Search.path) {
            SearchScreen()
        }
    }
}