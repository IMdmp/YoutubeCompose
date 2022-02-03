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
fun VideoPlayer(dataSource: PlayerDataSource,dataItem: DataItem) {
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
                val mediaSource = getMediaItem(dataSource,dataItem)
                withContext(Dispatchers.Main) {
                    player.setMediaSource(mediaSource)
                }
            }

            player.prepare()
            player.playWhenReady = playWhenReady

        }

        AndroidView(factory = {
            playerView
        })
    }
}

fun getMediaItem(dataSource: PlayerDataSource, dataItem: DataItem): MediaSource {

    val streamInfo = StreamInfo.getInfo(NewPipe.getService(0), dataItem.streamUrl)
    val firstSource = streamInfo.videoOnlyStreams!![0]
    val uri = Uri.parse(firstSource.url)

    val overrideExtension = MediaFormat.getSuffixById(firstSource.formatId)
    @C.ContentType val type: Int =
        if (TextUtils.isEmpty(overrideExtension)) Util.inferContentType(uri) else Util.inferContentType(
            ".$overrideExtension"
        )


    val factory: MediaSourceFactory = when (type) {
        C.TYPE_SS -> dataSource.liveSsMediaSourceFactory
        C.TYPE_DASH -> dataSource.dashMediaSourceFactory
        C.TYPE_HLS -> dataSource.hlsMediaSourceFactory
        C.TYPE_OTHER -> dataSource.extractorMediaSourceFactory
        else -> throw IllegalStateException("Unsupported type: $type")
    }

    return factory.createMediaSource(
        MediaItem.Builder()
            .setUri(uri)
            .build()
    )
}

