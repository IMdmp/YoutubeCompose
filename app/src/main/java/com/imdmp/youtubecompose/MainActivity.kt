package com.imdmp.youtubecompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.FragmentActivity
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.imdmp.youtubecompose.features.home.HomeListViewModel
import com.imdmp.youtubecompose.features.home.VideoListScreen
import com.imdmp.youtubecompose.player.PlayerDataSource
import com.imdmp.youtubecompose.ui.theme.YoutubeComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VideoListScreen(homeListViewModel = HomeListViewModel())
        }
    }

}