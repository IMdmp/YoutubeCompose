package com.imdmp.youtubecompose.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.imdmp.youtubecompose.features.videolist.ViewModelEvent

abstract class BaseViewModel : ViewModel() {
    private val observableEvents = MutableLiveData<ViewModelEvent>()

    fun observeViewModelEvents(): LiveData<ViewModelEvent> = observableEvents

    protected fun postViewModelEvent(event: ViewModelEvent) {
        observableEvents.postValue(event)
    }
}