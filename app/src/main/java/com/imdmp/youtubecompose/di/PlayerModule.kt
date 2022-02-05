package com.imdmp.youtubecompose.di

import android.content.Context
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.imdmp.youtubecompose.extractor.DownloaderImpl
import com.imdmp.youtubecompose.player.PlayerDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject

@InstallIn(SingletonComponent::class)
@Module
class PlayerModule @Inject constructor( ) {

    @Provides
    fun providesDataSource(@ApplicationContext context: Context): PlayerDataSource {
        return PlayerDataSource(
            context, DownloaderImpl.USER_AGENT,
            DefaultBandwidthMeter.Builder(context).build()
        )
    }
}