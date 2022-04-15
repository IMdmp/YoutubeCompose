package com.imdmp.youtubecompose

import android.os.Bundle
import android.view.View
import androidx.activity.compose.setContent
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.FragmentActivity
import com.imdmp.ui_core.theme.YoutubeComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : FragmentActivity(), BaseActivityCallbacks {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            YoutubeComposeTheme {
                MainAppScreen(baseActivityCallbacks = this)
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