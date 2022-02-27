package com.imdmp.youtubecompose.base.di

import android.content.Context
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.imdmp.youtubecompose.usecases.extractor.DownloaderImpl
import com.imdmp.youtubecompose.usecases.player.PlayerDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject

@InstallIn(SingletonComponent::class)
@Module
class PlayerModule @Inject constructor() {

    @Provides
    fun providesDataSource(@ApplicationContext context: Context): PlayerDataSource {
        return PlayerDataSource(
            context, DownloaderImpl.USER_AGENT,
            DefaultBandwidthMeter.Builder(context).build()
        )
    }
}


//private fun getVideoPlayer(context: Context): ExoPlayer {
//    return SimpleExoPlayer.Builder(context).build().apply {
//        addListener(object : Player.Listener {
//            override fun onPlaybackStateChanged(state: Int) {
//                super.onPlaybackStateChanged(state)
//                Timber.d("state changed.")
//                if (state == Player.STATE_READY) {
////                        videoPlayerViewModel.handleEvent(VideoEvent.VideoLoaded)
//                } else if (state == Player.EVENT_PLAYER_ERROR) {
////                        videoPlayerViewModel.handleEvent(VideoEvent.VideoError)
//                }
//            }
//        })
//    }
//}