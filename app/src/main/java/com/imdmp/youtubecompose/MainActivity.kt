package com.imdmp.youtubecompose

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.ExperimentalMaterialApi
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.LifecycleOwner
import com.imdmp.youtubecompose.base.BaseActivityCallbacks
import com.imdmp.youtubecompose.base.BaseViewModel
import com.imdmp.youtubecompose.base.ViewModelActionHandler
import com.imdmp.youtubecompose.features.videolist.VideoListViewModel
import com.imdmp.youtubecompose.features.videolist.ViewModelEvent
import com.imdmp.youtubecompose.features.videolist.events.VideoListEvents
import com.imdmp.youtubecompose.features.videoplayer.VideoPlayerEvents
import com.imdmp.youtubecompose.features.videoplayer.VideoPlayerViewModel
import com.imdmp.ytcore.YTCoreTheme
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalMaterialApi
@AndroidEntryPoint
class MainActivity : ComponentActivity(), BaseActivityCallbacks {

    private val videoPlayerViewModel: VideoPlayerViewModel by viewModels()
    private val videoListViewModel: VideoListViewModel by viewModels()
    private val mainViewModel: MainViewModel by viewModels()

    private val videoListActionHandler: ViewModelActionHandler = object : ViewModelActionHandler {
        override fun provideOwner(): LifecycleOwner {
            return this@MainActivity
        }

        override fun provideViewModel(): BaseViewModel {
            return videoListViewModel
        }

        override fun handleViewModelAction(event: ViewModelEvent) {
            if (event is VideoListEvents) {
                when (event) {
                    is VideoListEvents.BackPressedEvent -> {

                    }

                    is VideoListEvents.SearchIconClickedEvent -> {
                        videoListViewModel.screenState()
                    }

                    is VideoListEvents.VideoItemClickedEvent -> {
                        videoPlayerViewModel.updateUrl(event.videoListItem.streamUrl)

                        mainViewModel.updateMainScreenState(MainState.PLAYER)
                    }
                }
            }
            super.handleViewModelAction(event)
        }

    }

    private val videoPlayerActionHandler: ViewModelActionHandler = object : ViewModelActionHandler {
        override fun provideOwner(): LifecycleOwner {
            return this@MainActivity
        }

        override fun provideViewModel(): BaseViewModel {
            return videoPlayerViewModel
        }

        override fun handleViewModelAction(event: ViewModelEvent) {
            if (event is VideoPlayerEvents) {
                when (event) {
                    is VideoPlayerEvents.FullScreenPressed -> {
                        this@MainActivity.requestedOrientation =
                            ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                    }
                }

            }
            super.handleViewModelAction(event)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            YTCoreTheme {
                MainScreen()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        videoListActionHandler.onActivityStart()
        videoPlayerActionHandler.onActivityStart()
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
