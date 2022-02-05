package com.imdmp.youtubecompose.di

import com.imdmp.youtubecompose.player.PlayerDataSource
import com.imdmp.youtubecompose.usecases.GetVideoStreamUrlUseCase
import com.imdmp.youtubecompose.usecases.impl.GetVideoStreamUrlUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@InstallIn(SingletonComponent::class)
@Module
class UseCaseModule {

    @Provides
    fun GetVideoStreamUrlUseCase(dataSource: PlayerDataSource): GetVideoStreamUrlUseCase {
        return GetVideoStreamUrlUseCaseImpl(dataSource)
    }
}