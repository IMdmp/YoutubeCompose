package com.imdmp.youtubecompose.features.navigation.model

sealed class Destination(
    val path: String,
) {

    companion object {

    }


    object Home : Destination("home")
    object Player: Destination("player")
    object Search: Destination("search")

}