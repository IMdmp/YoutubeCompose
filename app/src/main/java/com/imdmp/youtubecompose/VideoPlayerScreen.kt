package com.imdmp.youtubecompose

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
import com.imdmp.youtubecompose.player.PlayerDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.schabi.newpipe.extractor.MediaFormat
import org.schabi.newpipe.extractor.NewPipe
import org.schabi.newpipe.extractor.kiosk.KioskInfo
import org.schabi.newpipe.extractor.services.youtube.YoutubeService
import org.schabi.newpipe.extractor.stream.StreamInfo
import timber.log.Timber
import java.lang.IllegalStateException

@Composable
fun VideoPlayer(dataSource: PlayerDataSource) {
    Box(modifier = Modifier.fillMaxSize()) {
        val sampleVideo =
            "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"
        val context = LocalContext.current
        val player = SimpleExoPlayer.Builder(context).build()
        val playerView = PlayerView(context)
        val playWhenReady by rememberSaveable {
            mutableStateOf(true)
        }
        playerView.player = player
        LaunchedEffect(player) {
            this.launch(Dispatchers.IO) {
                val mediaSource = getSampleMediaItem(dataSource)
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

suspend fun getSampleMediaItem(dataSource: PlayerDataSource): MediaSource {
    val info = KioskInfo.getInfo(YoutubeService(0), "https://www.youtube.com/feed/trending")

    //extract 'feed'
    val sampleLink = "https://www.youtube.com/watch?v=W7g82nFdWig"
    Timber.d("got this: $info")

    // extract video streams
    val streamInfo = StreamInfo.getInfo(NewPipe.getService(0), sampleLink)

//            val tag = MediaSourceTag(info, streamInfo, 0)


    Timber.d("stream info: $streamInfo")
    val sampleSource = streamInfo.videoOnlyStreams.get(0)

    //prepare exoplayer media source
    val sampleMediaSource = buildMediaSource(
        dataSource, sampleSource.url, "",
        MediaFormat.getSuffixById(sampleSource.getFormatId())
    )
    Timber.d("mediaSource; $sampleMediaSource")

    return sampleMediaSource

}

fun buildMediaSource(
    dataSource: PlayerDataSource,
    sourceUrl: String,
    cacheKey: String,
    overrideExtension: String,
): MediaSource {
    val uri = Uri.parse(sourceUrl)
    @C.ContentType val type: Int =
        if (TextUtils.isEmpty(overrideExtension)) Util.inferContentType(uri) else Util.inferContentType(
            ".$overrideExtension"
        )
    val factory: MediaSourceFactory
    factory = when (type) {
        C.TYPE_SS -> dataSource.getLiveSsMediaSourceFactory()
        C.TYPE_DASH -> dataSource.getDashMediaSourceFactory()
        C.TYPE_HLS -> dataSource.getHlsMediaSourceFactory()
        C.TYPE_OTHER -> dataSource.getExtractorMediaSourceFactory()
        else -> throw IllegalStateException("Unsupported type: $type")
    }
    return factory.createMediaSource(
        MediaItem.Builder()
            .setUri(uri)
            .setCustomCacheKey(cacheKey)
            .build()
    )
}