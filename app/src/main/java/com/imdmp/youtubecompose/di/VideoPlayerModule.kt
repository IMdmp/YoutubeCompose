package com.imdmp.youtubecompose.di

import android.content.Context
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.SimpleExoPlayer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.android.scopes.ViewModelScoped


@Module
@InstallIn(ActivityRetainedComponent::class)
class VideoPlayerModule {


    @Provides
    @ActivityRetainedScoped
    fun providesVideoPlayer(@ApplicationContext context: Context): ExoPlayer {
        return SimpleExoPlayer.Builder(context).build()
    }
}