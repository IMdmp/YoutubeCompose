package com.imdmp.youtubecompose

import android.os.Bundle
import android.view.View
import androidx.activity.compose.setContent
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.FragmentActivity
import com.imdmp.ui_core.theme.YoutubeComposeTheme
import com.imdmp.uihome.VideoListItem
import com.imdmp.uihome.VideoListScreen
import com.imdmp.uihome.VideoListScreenActions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : FragmentActivity(), BaseActivityCallbacks {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
//            YoutubeComposeTheme {
//                MainAppScreen(baseActivityCallbacks = this)
//            }
            val sampleVideoListItem = VideoListItem.default()

            val videoList = listOf(
                sampleVideoListItem.copy(title = "Long title but probably only one line long", author = "Author1", viewCount = 2L),
                sampleVideoListItem.copy(title = "This is a sample of a long title that can probably take two lines long", author = "Author2", viewCount = 1L),
                sampleVideoListItem.copy(title = "Title3", author = "Author3", viewCount = 5L),
                sampleVideoListItem.copy(title = "Title4", author = "Author4", viewCount = 10L)

            )
            YoutubeComposeTheme {
                VideoListScreen(
                    videoList = videoList,
                    videoListScreenActions = VideoListScreenActions.default()
                )
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