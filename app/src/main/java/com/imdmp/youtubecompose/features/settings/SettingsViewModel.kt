package com.imdmp.youtubecompose.features.settings

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.imdmp.youtubecompose.di.SharedPreferencesKeys
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : ViewModel() {
    val uiState = MutableStateFlow(SettingsState())


    fun toggleFeedEnabledSettings() {
        uiState.value = uiState.value.copy(feedEnabled = !uiState.value.feedEnabled)

        sharedPreferences.edit()
            .putBoolean(SharedPreferencesKeys.PREFERENCE_FEED_ENABLED, uiState.value.feedEnabled)
            .apply()

    }
}