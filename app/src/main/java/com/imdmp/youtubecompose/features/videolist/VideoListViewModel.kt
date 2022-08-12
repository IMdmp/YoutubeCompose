package com.imdmp.youtubecompose.features.videolist

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewModelScope
import com.imdmp.datarepository.YoutubeRepository
import com.imdmp.youtubecompose.base.BaseViewModel
import com.imdmp.youtubecompose.features.videolist.events.VideoListEvents
import com.imdmp.youtubecompose.features.videolist.model.VideoListItem
import com.imdmp.youtubecompose.features.videolist.model.VideoListScreenCallbacks
import com.imdmp.youtubecompose.features.videolist.search.SearchScreenCallbacks
import com.imdmp.youtubecompose.features.videolist.search.SearchState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VideoListViewModel @Inject constructor(
    private var youtubeRepository: YoutubeRepository
) : BaseViewModel() {
    private val searchState = mutableStateOf(SearchState())
    private val screenState = mutableStateOf(VideoListScreenState.DEFAULT)
    private val videoListState = mutableStateListOf<VideoListItem>()

    private val searchScreenCallbacks = object : SearchScreenCallbacks {
        override fun onSearchClicked(query: String) {
            viewModelScope.launch {
                screenState.value = VideoListScreenState.DEFAULT
            }
        }

        override fun onBackButtonClicked() {
            screenState.value = VideoListScreenState.DEFAULT
        }

        override fun textBoxClicked() {
        }

        override fun textBoxCancelClicked() {
            searchState.value = searchState.value.copy(searchText = TextFieldValue(""))
        }

        override fun onSearchTextValueChanged(newValue: TextFieldValue) {
            searchState.value = searchState.value.copy(searchText = newValue)
            viewModelScope.launch(Dispatchers.IO) {
                youtubeRepository.searchAutoSuggestion(newValue.text).collectLatest {
                    searchState.value = searchState.value.copy(suggestionList = it)

                }
            }
        }

        override fun suggestionSelected(suggestion: String) {
            searchState.value = searchState.value.copy(searchText = TextFieldValue(suggestion))
            onSearchClicked(suggestion)
        }
    }

    private val videoListDefaultScreenCallbacks = object : VideoListScreenCallbacks {
        override fun videoItemSelected(videoListItem: VideoListItem) {
            postViewModelEvent(VideoListEvents.VideoItemClickedEvent(videoListItem))
        }

        override fun searchClicked() {
            screenState.value = VideoListScreenState.SEARCH_ON
        }

        override fun profileClicked() {
//            TODO("Not yet implemented")
        }

        override fun onSearchClicked(query: String) {
        }

        override fun onBackButtonClicked() {
            searchState.value = searchState().copy(
                searchText = TextFieldValue("")
            )
        }

        override fun suggestionSelected(suggestion: String) {
        }

        override fun textBoxClicked() {
            screenState.value = VideoListScreenState.SEARCH_ON
        }

        override fun textBoxCancelClicked() {
            searchState.value = searchState.value.copy(searchText = TextFieldValue(""))
            screenState.value = VideoListScreenState.SEARCH_ON
        }


        override fun onSearchTextValueChanged(newValue: TextFieldValue) {
        }

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
        return videoListState
    }

    fun searchScreenCallbacks(): SearchScreenCallbacks {
        return searchScreenCallbacks
    }

    fun videoListDefaultScreenCallbacks(): VideoListScreenCallbacks {
        return videoListDefaultScreenCallbacks
    }

    fun loadQuery() {
        videoListState.clear()
        viewModelScope.launch(Dispatchers.IO) {
            setSearchStateLoading()
            val query = searchState.value.searchText.text
            val newList = if (query.isEmpty())
                youtubeRepository.getYTDataList().ytDataList.map {
                    VideoListItem(
                        it.thumbnail,
                        it.name,
                        it.uploaderName,
                        it.uploaderThumbnail, it.viewCount, streamUrl = it.url, uploadedDate = ""
                    )
                } else {
                youtubeRepository.search(query = query).ytDataList.map {
                    VideoListItem(
                        it.thumbnail,
                        it.name,
                        it.uploaderName,
                        it.uploaderThumbnail, it.viewCount, streamUrl = it.url, uploadedDate = ""
                    )
                }
            }
            videoListState.addAll(newList)
            setSearchStateDataAvailable()
        }

    }

    fun setSearchStateLoading() {
        searchState.value =
            searchState.value.copy(searchNetworkState = SearchState.SearchNetworkState.LOADING)
    }

    fun setSearchStateDataAvailable() {
        searchState.value =
            searchState.value.copy(searchNetworkState = SearchState.SearchNetworkState.DATA_AVAILABLE)

    }


}