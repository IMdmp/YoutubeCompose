package com.imdmp.youtubecompose.features.splash

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(val sharedPreferences: SharedPreferences) : ViewModel() {


}
