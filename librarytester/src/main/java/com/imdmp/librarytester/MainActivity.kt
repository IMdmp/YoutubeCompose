package com.imdmp.librarytester

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.google.android.exoplayer2.SimpleExoPlayer
import com.imdmp.datarepository.impl.NewPipeDataModelConverterImpl
import com.imdmp.datarepository.impl.NewPipeExtractorWrapperImpl
import com.imdmp.datarepository.impl.YoutubeRepositoryImpl
import com.imdmp.librarytester.ui.theme.YoutubeComposeTheme
import com.imdmp.ui_player.model.VideoPlayerComposeScreenState
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

class MainActivity : ComponentActivity() {


    val newPipeExtractorWrapperImpl = NewPipeExtractorWrapperImpl()
    val newPipeDataModelConverterImpl = NewPipeDataModelConverterImpl()
    val youtubeRepository =
        YoutubeRepositoryImpl(newPipeExtractorWrapperImpl, newPipeDataModelConverterImpl)

    //    val youtubeRepos
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val player = SimpleExoPlayer.Builder(this).build()
        val videoPlayerComposeScreenState = VideoPlayerComposeScreenState.forTesting()

        GlobalScope.launch {
            val list = youtubeRepository.searchAutoSuggestion("sample")

            list.collect {
                Timber.d("test: suggestion: ")
                it.forEach { item ->
                    Timber.d("item: ${item}")
                }
            }
        }

//            Timber.d("test: ${}")
//            YoutubeComposeTheme {
//                // A surface container using the 'background' color from the theme
//                VideoPlayerScreen(
//                    player = player,
//                    state = videoPlayerComposeScreenState,
//                    videoPlayerScreenCallbacks = VideoPlayerScreenCallbacks.default()
//                )
//            }
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