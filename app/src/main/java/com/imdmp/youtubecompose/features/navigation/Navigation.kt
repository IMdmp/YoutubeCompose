package com.imdmp.youtubecompose.features.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.Navigation
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.imdmp.youtubecompose.features.home.HomeListViewModel
import com.imdmp.youtubecompose.features.home.VideoListScreen
import com.imdmp.youtubecompose.features.navigation.model.Destination

@Composable
fun Navigation(modifier: Modifier = Modifier, navController: NavHostController) {

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Destination.Home.path
    ) {
        composable(Destination.Home.path) {
            VideoListScreen(homeListViewModel = HomeListViewModel())
        }
    }
}