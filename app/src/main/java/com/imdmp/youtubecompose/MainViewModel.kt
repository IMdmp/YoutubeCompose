package com.imdmp.youtubecompose

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class MainViewModel @Inject constructor() : ViewModel() {
    fun updateMainScreenState(mainState: MainState) {
        mainScreenState.value = mainState
    }

    val mainScreenState = mutableStateOf(MainState.DEFAULT)

}