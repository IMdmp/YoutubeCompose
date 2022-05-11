package com.imdmp.youtubecompose

import android.content.Context
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.android.utils.FlipperUtils
import com.facebook.flipper.plugins.inspector.DescriptorMapping
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
import com.facebook.flipper.plugins.network.FlipperOkhttpInterceptor
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import com.facebook.flipper.plugins.sharedpreferences.SharedPreferencesFlipperPlugin
import com.facebook.soloader.SoLoader
import okhttp3.OkHttpClient
import timber.log.Timber

object AppConfig {
    fun setup(context: Context): OkHttpClient.Builder {
        val okHttpBuilder = OkHttpClient.Builder()

        if (BuildConfig.DEBUG && FlipperUtils.shouldEnableFlipper(context)) {
            val networkFlipperPlugin = NetworkFlipperPlugin()
            SoLoader.init(context, false)

            okHttpBuilder
                .addNetworkInterceptor(FlipperOkhttpInterceptor(networkFlipperPlugin))
            AndroidFlipperClient.getInstance(context).apply {
                addPlugin(
                    InspectorFlipperPlugin(
                        context,
                        DescriptorMapping.withDefaults()
                    )
                )
                addPlugin(
                    SharedPreferencesFlipperPlugin(context)
                )
                addPlugin(networkFlipperPlugin)

                this.start()

            }
        }

        Timber.plant(Timber.DebugTree())
        return okHttpBuilder
    }
}
