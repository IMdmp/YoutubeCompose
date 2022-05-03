package com.imdmp.youtubecompose.base.di

import com.imdmp.datarepository.NewPipeDataModelConverter
import com.imdmp.datarepository.NewPipeExtractorWrapper
import com.imdmp.datarepository.YoutubeRepository
import com.imdmp.datarepository.impl.NewPipeDataModelConverterImpl
import com.imdmp.datarepository.impl.NewPipeExtractorWrapperImpl
import com.imdmp.datarepository.impl.YoutubeRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class RepositoryModule {

    @Provides
    fun providesYoutubeRepository(
        newPipeExtractorWrapper: NewPipeExtractorWrapper,
        newPipeDataModelConverter: NewPipeDataModelConverter
    ): YoutubeRepository {
        return YoutubeRepositoryImpl(
            newPipeDataModelConverter = newPipeDataModelConverter,
            newPipeExtractorWrapper = newPipeExtractorWrapper
        )
    }

    @Provides
    fun providesNewPipeExtractorWrapper(): NewPipeExtractorWrapper {
        return NewPipeExtractorWrapperImpl()
    }

    @Provides
    fun providesNewPipeConverter(): NewPipeDataModelConverter {
        return NewPipeDataModelConverterImpl()
    }
}