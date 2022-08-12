package com.imdmp.youtubecompose.features.videolist.search

import androidx.compose.ui.text.input.TextFieldValue

interface SearchScreenCallbacks : CustomTextFieldCallbacks {
    fun onSearchClicked(query: String)
    fun onBackButtonClicked()
    fun suggestionSelected(suggestion: String)

    companion object {
        fun default(): SearchScreenCallbacks = object : SearchScreenCallbacks {
            override fun onSearchClicked(query: String) {
                TODO("Not yet implemented")
            }

            override fun textBoxClicked() {
                TODO("Not yet implemented")
            }

            override fun onBackButtonClicked() {
                TODO("Not yet implemented")
            }

            override fun suggestionSelected(suggestion: String) {
                TODO("Not yet implemented")
            }

            override fun textBoxCancelClicked() {
                TODO("Not yet implemented")
            }

            override fun onSearchTextValueChanged(newValue: TextFieldValue) {
                TODO("Not yet implemented")
            }


        }
    }
}
