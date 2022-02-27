package com.imdmp.youtubecompose.features.splash

import android.window.SplashScreen
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.imdmp.youtubecompose.features.navigation.model.Destination
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController, splashViewModel: SplashViewModel = hiltViewModel()) {

    LaunchedEffect(key1 = Unit) {
        delay(500)
        navController.navigate(splashViewModel.startingRoute())
    }

    SplashScreen()
}

@Composable
fun SplashScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Icon(
            Icons.Default.PlayArrow,
            contentDescription = "",
            modifier = Modifier.size(48.dp)
        )
    }
}

@Preview
@Composable
fun PreviewSplashScreen() {
    SplashScreen()
}