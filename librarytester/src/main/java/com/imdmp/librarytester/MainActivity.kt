package com.imdmp.librarytester

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.google.android.exoplayer2.SimpleExoPlayer
import com.imdmp.librarytester.ui.theme.YoutubeComposeTheme
import com.imdmp.ui_player.VideoPlayerScreen
import com.imdmp.ui_player.model.VideoPlayerComposeScreenState
import com.imdmp.ui_player.model.VideoPlayerScreenCallbacks

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val player = SimpleExoPlayer.Builder(this).build()
        val videoPlayerComposeScreenState = VideoPlayerComposeScreenState.forTesting()
        setContent {
            YoutubeComposeTheme {
                // A surface container using the 'background' color from the theme
                VideoPlayerScreen(
                    player = player,
                    state = videoPlayerComposeScreenState,
                    videoPlayerScreenCallbacks = VideoPlayerScreenCallbacks.default()
                )
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