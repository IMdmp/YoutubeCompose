package com.imdmp.youtubecompose.features.navigation.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import java.net.URLEncoder

sealed class Destination(
    val title: String,
    val path: String,
    val icon: ImageVector? = null
) {

    companion object {
        const val STREAM_URL = "stream_url"
        const val VIDEO_LIST = "video_list"

        fun fromString(route: String?): Destination {
            return when (route) {
                VideoList.path -> VideoList
                Player.path -> Player
                Search.path -> Search
                Profile.path -> Profile
                else -> Search
            }
        }

    }

    object Splash : Destination("Splash", "splash")

    object VideoList : Destination("VideoList", "{$VIDEO_LIST}/videoList", Icons.Default.Home) {
        fun createRoute(query: String = ""): String {

            if (query.isEmpty()) {
                return VideoList.path
            }

            return "${URLEncoder.encode(query, "utf-8")}/videoList"
        }
    }

    object Player : Destination("Player", "{$STREAM_URL}/player") {
        fun createRoute(url: String): String {
            return "${URLEncoder.encode(url, "utf-8")}/player"
        }
    }

    object Search : Destination("Search", "search", Icons.Default.Search)

    object Profile : Destination("Profile", "profile", Icons.Default.Person)

    object FullScreenView : Destination("Full Screen View","fullscreenview")

    object Test:Destination("test","test")

}