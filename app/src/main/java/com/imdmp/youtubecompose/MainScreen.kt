package com.imdmp.youtubecompose

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.imdmp.youtubecompose.features.navigation.bottombar.BottomNavigationBar
import com.imdmp.youtubecompose.features.navigation.model.Destination
import com.imdmp.youtubecompose.features.videoplayer.DraggableVideoPlayer
import com.imdmp.youtubecompose.features.videoplayer.VideoPlayerViewModel
import kotlin.math.roundToInt

@Composable
fun MainScreen(
    navController: NavHostController = rememberNavController(),
) {
    val mainViewModel = hiltViewModel<MainViewModel>()
    val videoPlayerViewModel = hiltViewModel<VideoPlayerViewModel>()
    val mainScreenState = mainViewModel.mainScreenState

    val navBackStackEntry = navController.currentBackStackEntryAsState()

    val currentDestination by derivedStateOf {
        Destination.fromString(navBackStackEntry.value?.destination?.route)
    }
    val bottomBarOffsetHeightPx = remember { mutableStateOf(0f) }
    val isFullScreen = remember { mutableStateOf(false) }
    val systemUiController = rememberSystemUiController()
    val useDarkIcons = MaterialTheme.colors.isLight

    SideEffect {
        // Update all of the system bar colors to be transparent, and use
        // dark icons if we're in light theme
        systemUiController.setStatusBarColor(
            color = Color.Transparent,
            darkIcons = useDarkIcons
        )

        // setStatusBarsColor() and setNavigationBarColor() also exist
    }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                modifier = Modifier
                    .offset {
                        IntOffset(
                            x = 0,
                            y = -bottomBarOffsetHeightPx.value.roundToInt()
                        )
                    },
                currentDestination = currentDestination,
                onNavigate = { destination ->
                    navController.navigate(destination.path) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                })
        }
    ) { padding ->
        val bottomPadding: Dp by animateDpAsState(
            if (isFullScreen.value) {
                0.dp
            } else {
                padding.calculateBottomPadding()
            }
        )
        MainNavScreen(navController = navController)

        if (mainScreenState.value == MainState.PLAYER) {
            DraggableVideoPlayer(
                callback = {
                    val newOffset = (-150 + (150 * it))
                    bottomBarOffsetHeightPx.value = newOffset
                    isFullScreen.value = it < 0.5f
                },
                modifier = Modifier
                    .padding(bottom = bottomPadding)
                    .zIndex(3f),
                videoPlayerViewModel = videoPlayerViewModel
            )
        }
    }
}