package com.imdmp.youtubecompose.features.search

data class SearchState(
    val suggestionList: List<String> = listOf(),
    val searchText:String =""
) {
}