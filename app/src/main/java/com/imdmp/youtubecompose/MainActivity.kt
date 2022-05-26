package com.imdmp.youtubecompose

import android.os.Bundle
import android.view.View
import androidx.activity.compose.setContent
import androidx.compose.material.ExperimentalMaterialApi
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.FragmentActivity
import com.imdmp.datarepository.YoutubeRepository
import com.imdmp.ui_core.theme.YoutubeComposeTheme
import com.imdmp.youtubecompose_ui.uihome.VideoListItem
import dagger.hilt.android.AndroidEntryPoint
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
    val sampleUrl =
        "https://www.youtube.com/watch?v=rvskMHn0sqQ&t=4s"

    @OptIn(ExperimentalMotionApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            YoutubeComposeTheme {
                MainAppScreen(baseActivityCallbacks = this)
//                val videoPlayerViewModel = hiltViewModel<VideoPlayerViewModel>()
//
//                VideoListScreen(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .layoutId("video_list"),
//                    videoList = videoList,
//                    videoListScreenActions = VideoListScreenActions.default()
//                )
//                val screenState = remember { mutableStateOf(true) }
//                if (screenState.value) {
//                    VideoPlayerScreen(
//                        player = videoPlayerViewModel.player,
//                        state = videoPlayerViewModel.uiState.collectAsState().value,
//                        videoPlayerScreenCallbacks = videoPlayerViewModel,
//                        lifecycleOwner = LocalLifecycleOwner.current,
//                        streamUrl = sampleUrl
//                    ) {
//                        screenState.value = false
//                    }
//                }
            }

        }
    }

//    }

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

//iteration1

//MotionLayout(
//modifier = Modifier.fillMaxSize(),
//motionScene = MotionScene(content = motionScene), progress = (number)
//) {
//    VideoListScreen(
//        modifier = Modifier
//            .fillMaxSize()
//            .layoutId("video_list"),
//        videoList = videoList,
//        videoListScreenActions = VideoListScreenActions.default()
//    )
//
//    VideoPlayerScreen(
//        modifier = Modifier
//            .layoutId("video_screen")
//            .draggable(
//                orientation = Orientation.Vertical,
//                state = draggableState
//            ),
//        player = videoPlayerViewModel.player,
//        state = videoPlayerViewModel.uiState.collectAsState().value,
//        videoPlayerScreenCallbacks = videoPlayerViewModel,
//        lifecycleOwner = LocalLifecycleOwner.current,
//        streamUrl = sampleUrl
//    )
//}
