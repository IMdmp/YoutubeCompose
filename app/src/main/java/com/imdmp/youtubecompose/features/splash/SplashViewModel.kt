package com.imdmp.youtubecompose.features.splash

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.imdmp.youtubecompose.base.di.SharedPreferencesKeys
import com.imdmp.youtubecompose.features.ui.navigation.model.Destination
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(val sharedPreferences: SharedPreferences) : ViewModel() {

    fun startingRoute(): String {
        return if (
            sharedPreferences.getBoolean(
                SharedPreferencesKeys.PREFERENCE_FEED_ENABLED, false
            ).not()
        ) {
            Destination.Search.path
        } else {
            Destination.VideoList.createRoute("")
        }

    }
}