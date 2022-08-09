package com.imdmp.youtubecompose.base.di

import android.content.Context
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.imdmp.datarepository.usecase.GetVideoStreamUrlUseCase
import com.imdmp.datarepository.usecase.GetVideoStreamUrlUseCaseImpl
import com.imdmp.datarepository.utils.PlayerDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent


@InstallIn(SingletonComponent::class)
@Module
class UseCaseModule {
    companion object {
        const val USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; rv:78.0) Gecko/20100101 Firefox/78.0"
    }

    @Provides
    fun providesDataSource(@ApplicationContext context: Context): PlayerDataSource {
        return PlayerDataSource(
            context, USER_AGENT,
            DefaultBandwidthMeter.Builder(context).build()
        )
    }

    @Provides
    fun GetVideoStreamUrlUseCase(dataSource: PlayerDataSource): GetVideoStreamUrlUseCase {
        return GetVideoStreamUrlUseCaseImpl(dataSource)
    }
}
