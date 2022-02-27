package com.imdmp.youtubecompose.base.di

import com.imdmp.youtubecompose.usecases.GetVideoStreamUrlUseCase
import com.imdmp.youtubecompose.usecases.impl.GetVideoStreamUrlUseCaseImpl
import com.imdmp.youtubecompose.usecases.player.PlayerDataSource
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