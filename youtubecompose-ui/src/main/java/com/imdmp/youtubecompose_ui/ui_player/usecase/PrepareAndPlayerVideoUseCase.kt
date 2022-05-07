package com.imdmp.youtubecompose_ui.ui_player.usecase

import com.google.android.exoplayer2.ExoPlayer

interface PrepareAndPlayerVideoUseCase {
    suspend operator fun invoke(
        streamUrl: String, player: ExoPlayer
    )
}
