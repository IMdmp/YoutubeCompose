package com.imdmp.youtubecompose

import android.app.Application
import android.content.Context
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.android.utils.FlipperUtils
import com.facebook.flipper.plugins.inspector.DescriptorMapping
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
import com.facebook.flipper.plugins.network.FlipperOkhttpInterceptor
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import com.facebook.soloader.SoLoader
import com.imdmp.youtubecompose.usecases.extractor.DownloaderImpl
import com.imdmp.youtubecompose.usecases.extractor.DownloaderImpl.RECAPTCHA_COOKIES_KEY
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.DEBUG_PROPERTY_NAME
import kotlinx.coroutines.DEBUG_PROPERTY_VALUE_ON
import okhttp3.OkHttpClient
import org.schabi.newpipe.extractor.NewPipe
import org.schabi.newpipe.extractor.downloader.Downloader
import org.schabi.newpipe.extractor.localization.ContentCountry
import org.schabi.newpipe.extractor.localization.Localization
import timber.log.Timber
import java.util.*


@HiltAndroidApp
class YoutubeComposeApp : Application() {
    override fun onCreate() {
        super.onCreate()
        SoLoader.init(this@YoutubeComposeApp, false)
        val networkFlipperPlugin = NetworkFlipperPlugin()
        val okhttpBuilder = OkHttpClient.Builder()
        okhttpBuilder
            .addNetworkInterceptor(FlipperOkhttpInterceptor(networkFlipperPlugin))

        if (BuildConfig.DEBUG && FlipperUtils.shouldEnableFlipper(this@YoutubeComposeApp)) {
            val client = AndroidFlipperClient.getInstance(this@YoutubeComposeApp)
            client.addPlugin(
                InspectorFlipperPlugin(
                    this@YoutubeComposeApp,
                    DescriptorMapping.withDefaults()
                )
            )
            client.addPlugin(networkFlipperPlugin)
            client.start()
        }

        NewPipe.init(
            getDownloader(okhttpBuilder),
            getPreferredLocalization(this),
            ContentCountry(Locale.getDefault().country)
        )

        Timber.plant(Timber.DebugTree())
        System.setProperty(DEBUG_PROPERTY_NAME, DEBUG_PROPERTY_VALUE_ON)
    }


    fun getPreferredLocalization(
        context: Context
    ): Localization? {
        return Localization
            .fromLocale(Locale.getDefault())

    }

    protected fun getDownloader(okhttpBuilder: OkHttpClient.Builder?): Downloader? {
        val downloader = DownloaderImpl.init(okhttpBuilder)
        setCookiesToDownloader(downloader)
        return downloader
    }

    protected fun setCookiesToDownloader(downloader: DownloaderImpl) {
        val prefs = androidx.preference.PreferenceManager.getDefaultSharedPreferences(
            applicationContext
        )
        val key = applicationContext.getString(R.string.recaptcha_cookies_key)
        downloader.setCookie(RECAPTCHA_COOKIES_KEY, prefs.getString(key, null))
        downloader.updateYoutubeRestrictedModeCookies(applicationContext)
    }
}
