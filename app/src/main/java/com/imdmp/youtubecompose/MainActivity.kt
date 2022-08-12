package com.imdmp.youtubecompose

import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.ExperimentalMaterialApi
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.LifecycleOwner
import com.imdmp.youtubecompose.base.BaseViewModel
import com.imdmp.youtubecompose.base.ViewModelActionHandler
import com.imdmp.youtubecompose.features.MainScreenEvents
import com.imdmp.youtubecompose.features.videolist.VideoListViewModel
import com.imdmp.youtubecompose.features.videolist.ViewModelEvent
import com.imdmp.youtubecompose.features.videolist.events.VideoListEvents
import com.imdmp.youtubecompose.features.videoplayer.VideoPlayerEvents
import com.imdmp.youtubecompose.features.videoplayer.VideoPlayerViewModel
import com.imdmp.ytcore.YTCoreTheme
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalMaterialApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

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

    private val mainActionHandler: ViewModelActionHandler = object : ViewModelActionHandler {
        override fun provideOwner(): LifecycleOwner {
            return this@MainActivity
        }

        override fun provideViewModel(): BaseViewModel {
            return mainViewModel
        }

        override fun handleViewModelAction(event: ViewModelEvent) {
            if (event is MainScreenEvents) {
                when (event) {
                    is MainScreenEvents.MainStateValueChanged -> {
                        if (event.mainState == MainState.DEFAULT) {
                            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_USER
                        }
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

        val orientation = resources.configuration.orientation

        if (orientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            showSystemBars()
        } else {
            hideSystemBars()
        }
    }

    override fun onStart() {
        super.onStart()
        videoListActionHandler.onActivityStart()
        videoPlayerActionHandler.onActivityStart()
        mainActionHandler.onActivityStart()
    }

    private fun hideSystemBars() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val windowInsetsController =
                ViewCompat.getWindowInsetsController(window.decorView) ?: return
            // Configure the behavior of the hidden system bars
            windowInsetsController.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            @Suppress("DEPRECATION")
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
    }

    private fun showSystemBars() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val windowInsetsController =
                ViewCompat.getWindowInsetsController(window.decorView) ?: return

            windowInsetsController.show(WindowInsetsCompat.Type.systemBars())
        } else {
            @Suppress("DEPRECATION")
            window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
            @Suppress("DEPRECATION")
            window.addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN)

        }

    }

    override fun onBackPressed() {
        if (mainViewModel.backPressedHandled().not()) {
            super.onBackPressed()
        }

    }
}
