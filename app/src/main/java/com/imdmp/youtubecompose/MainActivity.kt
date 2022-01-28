package com.imdmp.youtubecompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.imdmp.youtubecompose.player.PlayerDataSource
import com.imdmp.youtubecompose.ui.theme.YoutubeComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dataSource = PlayerDataSource(
            this, DownloaderImpl.USER_AGENT,
            DefaultBandwidthMeter.Builder(this).build()
        )

        setContent {
            YoutubeComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    VideoPlayer(dataSource)
                }
            }
        }
    }
}



@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    YoutubeComposeTheme {
        Greeting("Android")
    }
}