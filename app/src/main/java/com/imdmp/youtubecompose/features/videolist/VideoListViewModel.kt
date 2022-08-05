package com.imdmp.youtubecompose.features.videolist

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.imdmp.youtubecompose.base.BaseViewModel
import com.imdmp.youtubecompose.features.videolist.events.VideoListEvents
import com.imdmp.youtubecompose.features.videolist.model.DummyDataProvider
import com.imdmp.youtubecompose.features.videolist.model.VideoListItem
import com.imdmp.youtubecompose.features.videolist.model.VideoListScreenCallbacks
import com.imdmp.youtubecompose.features.videolist.search.SearchScreenCallbacks

import com.imdmp.youtubecompose.features.videolist.search.SearchState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class VideoListViewModel @Inject constructor() : BaseViewModel() {
    private val searchState = mutableStateOf(SearchState())
    private val screenState = mutableStateOf(VideoListScreenState.DEFAULT)


    private val searchScreenCallbacks = object : SearchScreenCallbacks {
        override fun onSearchClicked(query: String) {
            viewModelScope.launch {
                searchState.value =
                    searchState.value.copy(searchNetworkState = SearchState.SearchNetworkState.LOADING)
                screenState.value = VideoListScreenState.DEFAULT
                delay(500L) // simulate network call
                searchState.value =
                    searchState.value.copy(searchNetworkState = SearchState.SearchNetworkState.DATA_AVAILABLE)
            }
        }

        override fun onBackButtonClicked() {
            screenState.value = VideoListScreenState.DEFAULT
        }

        override fun textBoxClicked() {
        }

        override fun textBoxCancelClicked() {
            searchState.value = searchState.value.copy(searchText = "")
        }

        override fun onSearchTextValueChanged(newValue: String) {
            searchState.value = searchState.value.copy(searchText = newValue)
        }

        override fun suggestionSelected() {}
    }

    private val videoListDefaultScreenCallbacks = object : VideoListScreenCallbacks {
        override fun videoItemSelected(videoListItem: VideoListItem) {
            postViewModelEvent(VideoListEvents.VideoItemClickedEvent(videoListItem))
        }

        override fun searchClicked() {
            screenState.value = VideoListScreenState.SEARCH_ON
        }

        override fun profileClicked() {
            TODO("Not yet implemented")
        }

        override fun onSearchClicked(query: String) {
        }

        override fun onBackButtonClicked() {
            searchState.value = searchState().copy(
                searchText = ""
            )
        }

        override fun suggestionSelected() {
        }

        override fun textBoxClicked() {
            screenState.value = VideoListScreenState.SEARCH_ON
        }

        override fun textBoxCancelClicked() {
            searchState.value = searchState.value.copy(searchText = "")
            screenState.value = VideoListScreenState.SEARCH_ON
        }


        override fun onSearchTextValueChanged(newValue: String) {
        }

    }

    fun updateQuery(query: String) {
        searchState.value = searchState.value.copy(searchText = query)
    }

    fun updateScreenIsRoot(isRoot: Boolean) {
        screenState.value = if (isRoot) {
            VideoListScreenState.DEFAULT
        } else {
            VideoListScreenState.SEARCH_ON
        }
    }

    fun screenState(): VideoListScreenState {
        return screenState.value
    }

    fun searchState(): SearchState {
        return searchState.value
    }

    fun provideVideoList(): List<VideoListItem> {
        return DummyDataProvider.provideData()
    }

    fun searchScreenCallbacks(): SearchScreenCallbacks {
        return searchScreenCallbacks
    }

    fun videoListDefaultScreenCallbacks(): VideoListScreenCallbacks {
        return videoListDefaultScreenCallbacks
    }


}