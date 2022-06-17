package com.imdmp.videoplayer.usecase

import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.MediaSourceFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PrepareAndPlayVideoPlayerUseCaseImpl(
    private val mediaSourceFactory: MediaSourceFactory
) : PrepareAndPlayerVideoUseCase {
    override suspend operator fun invoke(
        streamUrl: String,
        player: ExoPlayer,
    ) {
        val mediaSource = mediaSourceFactory.createMediaSource(
            MediaItem.Builder()
                .setUri(streamUrl)
                .build()
        )

        withContext(Dispatchers.Main) {
            player.setMediaSource(mediaSource)
            player.prepare()
            player.play()
        }
    }
}
