package com.imdmp.youtubecompose.usecases.impl

import android.net.Uri
import android.text.TextUtils
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.MediaSourceFactory
import com.google.android.exoplayer2.util.Util
import com.imdmp.youtubecompose.player.PlayerDataSource
import com.imdmp.youtubecompose.usecases.GetVideoStreamUrlUseCase
import org.schabi.newpipe.extractor.MediaFormat
import org.schabi.newpipe.extractor.NewPipe
import org.schabi.newpipe.extractor.stream.StreamInfo
import timber.log.Timber
import java.lang.IllegalStateException
import javax.inject.Inject

class GetVideoStreamUrlUseCaseImpl constructor(val dataSource: PlayerDataSource) :
    GetVideoStreamUrlUseCase {
    override suspend fun invoke(encryptedStreamUrl: String): MediaSource {
        val streamInfo = StreamInfo.getInfo(NewPipe.getService(0), encryptedStreamUrl)
        val firstSource = streamInfo.videoOnlyStreams!![0]

        Timber.d("first source: ${firstSource.url}")
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

        val mediaSource = factory.createMediaSource(
            MediaItem.Builder()
                .setUri(uri)
                .build()
        )
        Timber.d("test here media Source : $mediaSource")

        return mediaSource

    }
}