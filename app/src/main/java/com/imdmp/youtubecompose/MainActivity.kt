package com.imdmp.youtubecompose

import android.os.Bundle
import android.view.View
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.constraintlayout.compose.MotionLayout
import androidx.constraintlayout.compose.MotionScene
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.FragmentActivity
import com.imdmp.datarepository.YoutubeRepository
import com.imdmp.ui_core.theme.YoutubeComposeTheme
import com.imdmp.youtubecompose_ui.uihome.HomeScreen
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@ExperimentalMaterialApi
@AndroidEntryPoint
class MainActivity : FragmentActivity(), BaseActivityCallbacks {

    @Inject
    lateinit var youtubeRepository: YoutubeRepository

    @OptIn(ExperimentalMotionApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            YoutubeComposeTheme {
//                MainAppScreen(baseActivityCallbacks = this)
//                HomeScreen()
                var offsetX by remember { mutableStateOf(0f) }
                val draggableState = rememberDraggableState {
                    offsetX += it

                    Timber.d("offset: $offsetX")
                }
                val context = LocalContext.current
                val motionScene = remember {
                    context.resources
                        .openRawResource(R.raw.motion_scene)
                        .readBytes()
                        .decodeToString()
                }
//                Box(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .background(Color.Green)
//                        .layoutId("video_screen")
//                        .draggable(
//                            orientation = Orientation.Vertical,
//                            state = draggableState
//                        )
//                )
                MotionLayout(
                    modifier = Modifier.fillMaxSize(),
                    motionScene = MotionScene(content = motionScene), progress = 0.5f ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Green)
                            .layoutId("video_screen")
                            .draggable(
                                orientation = Orientation.Vertical,
                                state = draggableState
                            )
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
}
