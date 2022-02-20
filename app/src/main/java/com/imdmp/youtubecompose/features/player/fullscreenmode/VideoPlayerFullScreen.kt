package com.imdmp.youtubecompose.features.player.fullscreenmode

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.pm.ActivityInfo
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.imdmp.youtubecompose.BaseActivityCallbacks
import timber.log.Timber

@Composable
fun VideoPlayerFullScreen(baseActivityCallbacks: BaseActivityCallbacks) {
    Box(
        Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {

    }


}
