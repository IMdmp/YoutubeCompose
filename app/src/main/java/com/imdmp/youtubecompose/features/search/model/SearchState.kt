package com.imdmp.youtubecompose.features.search.model

data class SearchState(
    val suggestionList: List<String> = listOf(),
    val searchText:String =""
) {
}