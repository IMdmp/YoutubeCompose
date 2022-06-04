package com.imdmp.youtubecompose.features.search.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imdmp.datarepository.YoutubeRepository
import com.imdmp.youtubecompose_ui.search.SearchState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val youtubeRepository: YoutubeRepository
) : ViewModel() {

    val searchState = MutableStateFlow(SearchState())

    fun getSuggestions(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            youtubeRepository.searchAutoSuggestion(query = query).collect {
                searchState.value.suggestionList.add(it)
            }
        }
    }

    fun updateSearchText(text: String) {
        searchState.value = searchState.value.copy(searchText = text)
    }
}
