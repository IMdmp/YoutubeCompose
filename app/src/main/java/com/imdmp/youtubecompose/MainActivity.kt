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
import com.imdmp.youtubecompose_ui.uihome.VideoListItem
import com.imdmp.youtubecompose_ui.uihome.VideoListScreen
import com.imdmp.youtubecompose_ui.uihome.VideoListScreenActions
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@ExperimentalMaterialApi
@AndroidEntryPoint
class MainActivity : FragmentActivity(), BaseActivityCallbacks {

    @Inject
    lateinit var youtubeRepository: YoutubeRepository
    val sampleVideoListItem = VideoListItem.default()

    val videoList = listOf(
        sampleVideoListItem.copy(
            title = "Long title but probably only one line long",
            author = "Author1",
            viewCount = 2L
        ),
        sampleVideoListItem.copy(
            title = "This is a sample of a long title that can probably take two lines long",
            author = "Author2",
            viewCount = 1L
        ),
        sampleVideoListItem.copy(title = "Title3", author = "Author3", viewCount = 5L),
        sampleVideoListItem.copy(title = "Title4", author = "Author4", viewCount = 10L)

    )

    @OptIn(ExperimentalMotionApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            YoutubeComposeTheme {
//                MainAppScreen(baseActivityCallbacks = this)
//                HomeScreen()
                var offsetY by remember { mutableStateOf(0f) }
                val draggableState = rememberDraggableState {
                    offsetY += it
                    Timber.d("it: ${it}")
                }

                val number = (offsetY / 1000).coerceAtLeast(0f).coerceAtMost(0.90f);
                Timber.d("number: $number")
                val context = LocalContext.current
                val motionScene = remember {
                    context.resources
                        .openRawResource(R.raw.motion_scene)
                        .readBytes()
                        .decodeToString()
                }
                MotionLayout(
                    modifier = Modifier.fillMaxSize(),
                    motionScene = MotionScene(content = motionScene), progress = (number)
                ) {

                    VideoListScreen(
                        modifier = Modifier
                            .fillMaxSize()
                            .layoutId("video_list"),
                        videoList = videoList,
                        videoListScreenActions = VideoListScreenActions.default()
                    )
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
