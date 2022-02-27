package com.imdmp.youtubecompose.base.utils

import android.net.Uri
import android.text.TextUtils
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.MediaSourceFactory
import com.google.android.exoplayer2.util.Util
import com.imdmp.youtubecompose.usecases.player.PlayerDataSource


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