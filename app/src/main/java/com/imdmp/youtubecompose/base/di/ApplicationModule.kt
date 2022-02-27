package com.imdmp.youtubecompose.base.di

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.imdmp.youtubecompose.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent


@InstallIn(SingletonComponent::class)
@Module
class ApplicationModule {
    private val PREFERENCES_NAME = BuildConfig.APPLICATION_ID

    @Provides
    fun providesSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE)

    }
}