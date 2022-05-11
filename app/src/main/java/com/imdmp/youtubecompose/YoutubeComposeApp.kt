package com.imdmp.youtubecompose

import android.app.Application
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
import java.util.*


@HiltAndroidApp
class YoutubeComposeApp : Application() {
    override fun onCreate() {
        super.onCreate()
        val okhttpBuilder = AppConfig.setup(this)

        NewPipe.init(
            getDownloader(okhttpBuilder),
            getPreferredLocalization(),
            ContentCountry(Locale.getDefault().country)
        )
        System.setProperty(DEBUG_PROPERTY_NAME, DEBUG_PROPERTY_VALUE_ON)
    }


    private fun getPreferredLocalization(): Localization? {
        return Localization
            .fromLocale(Locale.getDefault())

    }

    private fun getDownloader(okhttpBuilder: OkHttpClient.Builder?): Downloader? {
        val downloader = DownloaderImpl.init(okhttpBuilder)
        setCookiesToDownloader(downloader)
        return downloader
    }

    private fun setCookiesToDownloader(downloader: DownloaderImpl) {
        val prefs = androidx.preference.PreferenceManager.getDefaultSharedPreferences(
            applicationContext
        )
        val key = applicationContext.getString(R.string.recaptcha_cookies_key)
        downloader.setCookie(RECAPTCHA_COOKIES_KEY, prefs.getString(key, null))
        downloader.updateYoutubeRestrictedModeCookies(applicationContext)
    }
}
