package com.imdmp.youtubecompose

import android.os.Bundle
import android.view.View
import androidx.activity.compose.setContent
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.FragmentActivity
import androidx.hilt.navigation.compose.hiltViewModel
import com.imdmp.ui_core.theme.YoutubeComposeTheme
import com.imdmp.youtubecompose.features.videoplayer.VideoPlayerViewModel
import com.imdmp.youtubecompose_ui.ui_player.VideoPlayerScreen
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalMaterialApi
@AndroidEntryPoint
class MainActivity : FragmentActivity(), BaseActivityCallbacks {
    var showVideoScreen = mutableStateOf(false)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val url = remember { mutableStateOf("") }

            YoutubeComposeTheme {
                MainAppScreen(
                    baseActivityCallbacks = this,
                    mainScreenCallback = object : MainScreenCallback {
                        override fun openVideoScreen(streamUrl: String) {
                            showVideoScreen.value = true
                            url.value = streamUrl
                        }
                    })

                if (showVideoScreen.value) {
                    OpenVideoScreen(
                        streamUrl = url.value,
                        videoScreenClosedCallback = showVideoScreen
                    )
                }
            }

        }
    }

    override fun setOrientation(activityInfo: Int) {
        this.let {
            it.requestedOrientation = activityInfo
            hideSystemBars(it.window.decorView)
        }
    }

    private fun hideSystemBars(view: View) {
        val windowInsetsController =
            ViewCompat.getWindowInsetsController(view) ?: return
        // Configure the behavior of the hidden system bars
        windowInsetsController.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        // Hide both the status bar and the navigation bar
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (showVideoScreen.value) {
            showVideoScreen.value = false
        }
    }
}

interface MainScreenCallback {
    fun openVideoScreen(streamUrl: String)
}

@OptIn(ExperimentalMotionApi::class)
@Composable
fun OpenVideoScreen(streamUrl: String, videoScreenClosedCallback: MutableState<Boolean>) {
    val videoPlayerViewModel = hiltViewModel<VideoPlayerViewModel>()

    VideoPlayerScreen(
        player = videoPlayerViewModel.player,
        state = videoPlayerViewModel.uiState.collectAsState().value,
        videoPlayerScreenCallbacks = videoPlayerViewModel,
        lifecycleOwner = LocalLifecycleOwner.current,
        streamUrl = streamUrl
    ) {
        videoScreenClosedCallback.value = false
    }
}

