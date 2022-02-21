package com.imdmp.youtubecompose.features.splash

import android.window.SplashScreen
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.imdmp.youtubecompose.features.navigation.model.Destination
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController, splashViewModel: SplashViewModel = hiltViewModel()) {

    LaunchedEffect(key1 = Unit) {
        delay(700)
        navController.navigate(splashViewModel.startingRoute())
    }

    Column(verticalArrangement = Arrangement.Center) {
        Icon(Icons.Default.PlayArrow, contentDescription = "", modifier = Modifier.size(48.dp))
    }
}