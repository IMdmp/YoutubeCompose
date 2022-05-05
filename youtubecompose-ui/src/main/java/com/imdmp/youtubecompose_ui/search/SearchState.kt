package com.imdmp.youtubecompose_ui.search

data class SearchState(
    val suggestionList: List<String> = listOf(),
    val searchText:String =""
) {
}
