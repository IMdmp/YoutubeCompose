package com.imdmp.youtubecompose

import androidx.compose.runtime.mutableStateOf
import com.imdmp.youtubecompose.base.BaseViewModel
import com.imdmp.youtubecompose.features.MainScreenEvents
import javax.inject.Inject

class MainViewModel @Inject constructor() : BaseViewModel() {
    fun updateMainScreenState(mainState: MainState) {
        mainScreenState.value = mainState
        postViewModelEvent(MainScreenEvents.MainStateValueChanged(mainState = mainState))
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