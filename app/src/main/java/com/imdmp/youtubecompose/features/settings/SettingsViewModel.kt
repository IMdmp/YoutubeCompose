package com.imdmp.youtubecompose.features.settings

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class SettingsViewModel : ViewModel() {
    val uiState = MutableStateFlow(SettingsState())

    fun toggleFeedEnabledSettings() {
        uiState.value = uiState.value.copy(feedEnabled = !uiState.value.feedEnabled)
    }
}