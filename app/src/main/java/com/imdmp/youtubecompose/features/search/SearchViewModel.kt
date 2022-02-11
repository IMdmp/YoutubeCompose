package com.imdmp.youtubecompose.features.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.schabi.newpipe.extractor.NewPipe
import org.schabi.newpipe.extractor.suggestion.SuggestionExtractor

class SearchViewModel : ViewModel() {
    val extractor: SuggestionExtractor = NewPipe.getService(0).suggestionExtractor
    val searchState = MutableStateFlow(SearchState())

    fun getSuggestions(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val extractor: SuggestionExtractor = NewPipe.getService(0)
                .suggestionExtractor

            val listRes = extractor.suggestionList(query)
            searchState.value = searchState.value.copy(listRes)
        }
    }

    fun updateSearchText(text: String) {
        searchState.value = searchState.value.copy(searchText = text)
    }
}