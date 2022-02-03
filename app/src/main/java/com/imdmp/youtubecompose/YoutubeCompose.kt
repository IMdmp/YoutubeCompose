package com.imdmp.youtubecompose

import android.app.Application
import android.content.Context
import android.preference.PreferenceManager
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.android.utils.FlipperUtils
import com.facebook.flipper.core.FlipperClient
import com.facebook.flipper.plugins.inspector.DescriptorMapping
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
import com.facebook.soloader.SoLoader
import com.imdmp.youtubecompose.DownloaderImpl.RECAPTCHA_COOKIES_KEY
import org.schabi.newpipe.extractor.NewPipe
import org.schabi.newpipe.extractor.downloader.Downloader
import org.schabi.newpipe.extractor.localization.ContentCountry
import org.schabi.newpipe.extractor.localization.Localization
import java.util.*

class YoutubeCompose : Application() {
    override fun onCreate() {
        super.onCreate()
        NewPipe.init(
            getDownloader(),
            getPreferredLocalization(this),
            ContentCountry(Locale.getDefault().country)
        )
        SoLoader.init(this, false)

        if (BuildConfig.DEBUG && FlipperUtils.shouldEnableFlipper(this)) {
            val client: FlipperClient = AndroidFlipperClient.getInstance(this)
            client.addPlugin(InspectorFlipperPlugin(this, DescriptorMapping.withDefaults()))
            client.start()
        }

        //todo:
        // convert all the other activities to fragment and use navigation components
        // use navigation components.
        // use dagger2- hilt to inject components.
        // add listeners to handle changing.
        // implement search
        // add bottom nav
        // integrate google account to get feed?
        // integrate to add subscriptions?

    }

    fun getPreferredLocalization(
        context: Context
    ): Localization? {
        return Localization
                .fromLocale(Locale.getDefault())

    }

    protected fun getDownloader(): Downloader? {
        val downloader = DownloaderImpl.init(null)
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