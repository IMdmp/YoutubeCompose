package com.imdmp.youtubecompose

import android.app.Application
import android.content.Context
import com.imdmp.youtubecompose.usecases.extractor.DownloaderImpl
import com.imdmp.youtubecompose.usecases.extractor.DownloaderImpl.RECAPTCHA_COOKIES_KEY
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.DEBUG_PROPERTY_NAME
import kotlinx.coroutines.DEBUG_PROPERTY_VALUE_ON
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
        NewPipe.init(
            getDownloader(),
            getPreferredLocalization(this),
            ContentCountry(Locale.getDefault().country)
        )

        AppConfig.setup(this@YoutubeComposeApp)

        Timber.plant(Timber.DebugTree())
        System.setProperty(DEBUG_PROPERTY_NAME, DEBUG_PROPERTY_VALUE_ON)
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