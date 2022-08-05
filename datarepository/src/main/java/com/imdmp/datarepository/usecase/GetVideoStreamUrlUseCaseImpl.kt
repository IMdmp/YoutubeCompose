package com.imdmp.datarepository.usecase

import android.net.Uri
import android.text.TextUtils
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.MediaSourceFactory
import com.google.android.exoplayer2.util.Util
import com.imdmp.datarepository.utils.PlayerDataSource
import org.schabi.newpipe.extractor.MediaFormat
import org.schabi.newpipe.extractor.stream.VideoStream

class GetVideoStreamUrlUseCaseImpl constructor(
    private val dataSource: PlayerDataSource,
) :
    GetVideoStreamUrlUseCase {
    override suspend fun invoke(url: VideoStream): MediaSource {
        val uri = Uri.parse(url.url)

        val overrideExtension = MediaFormat.getSuffixById(url.formatId)
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
}
