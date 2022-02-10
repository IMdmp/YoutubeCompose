package com.imdmp.youtubecompose.features.navigation.model

import java.net.URLEncoder

sealed class Destination(
    val path: String,
) {

    companion object {
        const val STREAM_URL = "stream_url"
        const val VIDEO_LIST = "video_list"
    }


    object VideoList : Destination("{$VIDEO_LIST}/videoList") {
        fun createRoute(query: String): String {
            return "${URLEncoder.encode(query, "utf-8")}/videoList"
        }
    }

    object Player : Destination("{$STREAM_URL}/player") {
        fun createRoute(url: String): String {
            return "${URLEncoder.encode(url, "utf-8")}/player"
        }
    }

    object Search : Destination("search")

}