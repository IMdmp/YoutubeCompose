package com.imdmp.youtubecompose.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import android.content.Context.MODE_PRIVATE
import com.imdmp.youtubecompose.BuildConfig
import dagger.hilt.android.qualifiers.ApplicationContext


@InstallIn(SingletonComponent::class)
@Module
class ApplicationModule {
    private val PREFERENCES_NAME = BuildConfig.APPLICATION_ID

    @Provides
    fun providesSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE)

    }
}