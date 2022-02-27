package com.imdmp.youtubecompose.features.search.model

interface SearchScreenActions {
    fun onSearchClicked(query:String)

    companion object{
        fun default(): SearchScreenActions = object : SearchScreenActions {
            override fun onSearchClicked(query: String) {
                TODO("Not yet implemented")
            }

        }
    }
}