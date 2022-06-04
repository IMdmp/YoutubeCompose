package com.imdmp.youtubecompose_ui.search

data class SearchState(
    val suggestionList: MutableList<String> = mutableListOf(),
    val searchText: String = ""
) {
}
