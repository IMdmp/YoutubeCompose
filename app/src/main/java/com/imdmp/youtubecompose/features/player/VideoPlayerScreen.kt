package com.imdmp.youtubecompose.features.player

import android.net.Uri
import android.text.TextUtils
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.MediaSourceFactory
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.util.Util
import com.imdmp.youtubecompose.features.home.DataItem
import com.imdmp.youtubecompose.player.PlayerDataSource
import com.imdmp.youtubecompose.usecases.GetVideoStreamUrlUseCase
import com.imdmp.youtubecompose.utils.buildMediaSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.schabi.newpipe.extractor.MediaFormat
import org.schabi.newpipe.extractor.NewPipe
import org.schabi.newpipe.extractor.kiosk.KioskInfo
import org.schabi.newpipe.extractor.search.SearchExtractor
import org.schabi.newpipe.extractor.services.youtube.YoutubeService
import org.schabi.newpipe.extractor.services.youtube.extractors.YoutubeSearchExtractor
import org.schabi.newpipe.extractor.stream.StreamInfo
import timber.log.Timber
import java.lang.IllegalStateException

@Composable
fun VideoPlayerScreen(videoPlayerViewModel: VideoPlayerViewModel, dataItem: DataItem) {

    var getVideoStreamUrlUseCase: GetVideoStreamUrlUseCase
    Box(modifier = Modifier.fillMaxSize()) {
        val context = LocalContext.current
        val player = SimpleExoPlayer.Builder(context).build()
        val playerView = PlayerView(context)
        val playWhenReady by rememberSaveable {
            mutableStateOf(true)
        }
        playerView.player = player
        LaunchedEffect(player) {
            this.launch(Dispatchers.IO) {
//                val mediaSource = getVideoStreamUrlUseCase(dataItem.streamUrl)
//                withContext(Dispatchers.Main) {
//                    player.setMediaSource(mediaSource)
//                }
            }

            player.prepare()
            player.playWhenReady = playWhenReady

        }

        AndroidView(factory = {
            playerView
        })
    }
}
