package com.imdmp.youtubecompose

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class MainViewModel @Inject constructor() : ViewModel() {
    fun updateMainScreenState(mainState: MainState) {
        mainScreenState.value = mainState
    }

    //return true if handled
    fun backPressedHandled(): Boolean {
        if (mainScreenState.value == MainState.PLAYER) {
            updateMainScreenState(MainState.DEFAULT)
            return true
        }
        return false
    }

    val mainScreenState = mutableStateOf(MainState.DEFAULT)

}