package com.imdmp.youtubecompose

import android.app.Application
import com.imdmp.datarepository.DataRepoConfig
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.DEBUG_PROPERTY_NAME
import kotlinx.coroutines.DEBUG_PROPERTY_VALUE_ON

@HiltAndroidApp
class YoutubeComposeApp : Application() {
    override fun onCreate() {
        super.onCreate()
        val okhttpBuilder = AppConfig.setup(this)
        val sharedPrefs = androidx.preference.PreferenceManager.getDefaultSharedPreferences(
            applicationContext
        )
        DataRepoConfig.init(okhttpBuilder, getString(R.string.recaptcha_cookies_key), sharedPrefs)

        System.setProperty(DEBUG_PROPERTY_NAME, DEBUG_PROPERTY_VALUE_ON)
    }


}
