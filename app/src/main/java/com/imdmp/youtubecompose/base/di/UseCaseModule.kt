package com.imdmp.youtubecompose.base.di

import android.content.Context
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.imdmp.datarepository.usecase.GetVideoStreamUrlUseCase
import com.imdmp.datarepository.usecase.GetVideoStreamUrlUseCaseImpl
import com.imdmp.datarepository.utils.DownloaderImpl
import com.imdmp.datarepository.utils.PlayerDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent


@InstallIn(SingletonComponent::class)
@Module
class UseCaseModule {

    @Provides
    fun providesDataSource(@ApplicationContext context: Context): PlayerDataSource {
        return PlayerDataSource(
            context, DownloaderImpl.USER_AGENT,
            DefaultBandwidthMeter.Builder(context).build()
        )
    }

    @Provides
    fun GetVideoStreamUrlUseCase(dataSource: PlayerDataSource): GetVideoStreamUrlUseCase {
        return GetVideoStreamUrlUseCaseImpl(dataSource)
    }
}
