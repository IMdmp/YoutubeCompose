package com.imdmp.youtubecompose

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.google.android.exoplayer2.ExoPlayer
import com.imdmp.youtubecompose.features.player.Playback
import com.imdmp.youtubecompose.features.player.VideoPlayerScreenCallbacks
import com.imdmp.youtubecompose.features.player.VideoPlayerViewModel
import com.imdmp.youtubecompose.features.player.fullscreenmode.VideoPlayerFullScreen
import com.imdmp.youtubecompose.features.theme.YoutubeComposeTheme
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : FragmentActivity(), BaseActivityCallbacks {

    @Inject
    lateinit var player: ExoPlayer

    val sampleUrl = "https://www.youtube.com/watch?v=iwa0SLUMDAs"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            YoutubeComposeTheme {
                val viewModel: VideoPlayerViewModel = viewModel()

                MainAppScreen(baseActivityCallbacks = this)


//                Playback(
//                    player = player, streamUrl = sampleUrl, navController = rememberNavController(
//                    ), playerScreenCallbacks = viewModel
//                )

//                val isFullScreen = remember {
//                    mutableStateOf(false)
//                }
//
//                if (isFullScreen.value) {
//                    VideoPlayerFullScreen(baseActivityCallbacks = this)
//                } else {
//
//                    Column {
//                        Button(onClick = {
//                            setOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
//                            isFullScreen.value = isFullScreen.value.not()
//                        }) {
//                            Text(text = "To full screen")
//                        }
//                    }
//                }

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
}