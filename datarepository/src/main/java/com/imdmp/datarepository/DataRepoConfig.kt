package com.imdmp.datarepository

import android.content.SharedPreferences
import com.imdmp.datarepository.utils.DownloaderImpl
import okhttp3.OkHttpClient
import org.schabi.newpipe.extractor.NewPipe
import org.schabi.newpipe.extractor.downloader.Downloader
import org.schabi.newpipe.extractor.localization.ContentCountry
import org.schabi.newpipe.extractor.localization.Localization
import java.util.*

object DataRepoConfig {
    fun init(
        okhttpBuilder: OkHttpClient.Builder?,
        captchaKey: String,
        sharedPreferences: SharedPreferences
    ) {
        NewPipe.init(
            getDownloader(okhttpBuilder, sharedPreferences, captchaKey),
            getPreferredLocalization(),
            ContentCountry(Locale.getDefault().country)
        )
    }


    private fun getPreferredLocalization(): Localization? {
        return Localization
            .fromLocale(Locale.getDefault())

    }

    private fun getDownloader(
        okhttpBuilder: OkHttpClient.Builder?,
        sharedPreferences: SharedPreferences,
        captchaKey: String
    ): Downloader? {
        val downloader = DownloaderImpl.init(okhttpBuilder)
        setCookiesToDownloader(downloader, sharedPreferences, captchaKey)
        return downloader
    }

    private fun setCookiesToDownloader(
        downloader: DownloaderImpl,
        sharedPreferences: SharedPreferences,
        captchaKey: String
    ) {
        downloader.setCookie(
            DownloaderImpl.RECAPTCHA_COOKIES_KEY,
            sharedPreferences.getString(captchaKey, null)
        )
    }
}
