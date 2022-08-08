package com.imdmp.youtubecompose

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.imdmp.youtubecompose.features.navigation.model.Destination
import com.imdmp.youtubecompose.features.splash.SplashScreen

@Composable
fun MainNavScreen(
    navController: NavHostController = rememberNavController(),
    startDestination: String = Destination.Splash.path,
) {

    NavHost(
        modifier = Modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Destination.Splash.path) {
            SplashScreen()
        }


    }
}