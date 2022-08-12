package com.imdmp.youtubecompose.features

import com.imdmp.youtubecompose.MainState
import com.imdmp.youtubecompose.features.videolist.ViewModelEvent

sealed class MainScreenEvents : ViewModelEvent() {
    class MainStateValueChanged(val mainState: MainState) : MainScreenEvents()
}