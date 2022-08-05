package com.imdmp.youtubecompose.features.videolist

import com.imdmp.youtubecompose.base.ViewModelActionHandler

abstract class ViewModelEvent {
    var handled: Boolean = false
        private set

    open fun handle(activity: ViewModelActionHandler) {
        handled = true
    }
}