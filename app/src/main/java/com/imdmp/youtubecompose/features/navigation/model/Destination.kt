package com.imdmp.youtubecompose.features.navigation.model

import com.imdmp.youtubecompose.features.home.DataItem

sealed class Destination(
    val path: String,
) {

    companion object {
        const val STREAM_URL = "stream_url"
    }


    object Home : Destination("home")
    object Player : Destination("{$STREAM_URL}/player") {
        fun createRoute(url: String) = "$url/player"
    }

    object Search : Destination("search")

}