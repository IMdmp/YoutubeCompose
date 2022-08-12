package com.imdmp.youtubecompose.features.videolist.search

import androidx.compose.ui.text.input.TextFieldValue

data class SearchState(
    val suggestionList: List<String> = listOf(),
    val searchText: TextFieldValue = TextFieldValue(),
    val searchNetworkState: SearchNetworkState = SearchNetworkState.EMPTY,
) {
    enum class SearchNetworkState {
        EMPTY, LOADING, DATA_AVAILABLE, ERROR
    }

    companion object {
        fun provideSample(): SearchState {
            val sample = TextFieldValue("kurzgesagt")
            val sampleSuggestions = listOf(
                "kurzgesagt in a nutshell",
                "kurzgesagt black hole",
                "kurzgesagt ants",
                "kurzgesagt the egg",
                "kurzgesagt climate change"
            )

            return SearchState(searchText = sample, suggestionList = sampleSuggestions)
        }

        fun idle(): SearchState {
            return SearchState()
        }
    }
}
