package com.imdmp.youtubecompose.base

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.imdmp.youtubecompose.features.videolist.ViewModelEvent

interface ViewModelActionHandler {

    fun onActivityStart() {
        provideViewModel().observeViewModelEvents().observe(provideOwner(), Observer {
            val event = it.takeUnless { it == null || it.handled } ?: return@Observer
            handleViewModelAction(event)
        })
    }

    fun provideOwner(): LifecycleOwner

    fun provideViewModel(): BaseViewModel

    fun handleViewModelAction(event: ViewModelEvent) {
        event.handle(this)
    }
}