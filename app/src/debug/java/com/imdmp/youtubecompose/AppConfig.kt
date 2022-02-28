package com.imdmp.youtubecompose

import android.content.Context
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.android.utils.FlipperUtils
import com.facebook.flipper.core.FlipperClient
import com.facebook.flipper.plugins.inspector.DescriptorMapping
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
import com.facebook.flipper.plugins.sharedpreferences.SharedPreferencesFlipperPlugin
import com.facebook.soloader.SoLoader

object AppConfig {
    fun setup(context: Context) {
        if (BuildConfig.DEBUG && FlipperUtils.shouldEnableFlipper(context)) {
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
                this.start()

            }
        }
    }
}
