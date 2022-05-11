package com.imdmp.youtubecompose

import android.content.Context
import okhttp3.OkHttpClient

object AppConfig {
    fun setup(context: Context): OkHttpClient.Builder {
        return OkHttpClient.Builder()
    }
}
