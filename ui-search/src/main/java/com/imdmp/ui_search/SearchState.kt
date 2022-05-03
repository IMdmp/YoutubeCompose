package com.imdmp.ui_search

data class SearchState(
    val suggestionList: List<String> = listOf(),
    val searchText:String =""
) {
}