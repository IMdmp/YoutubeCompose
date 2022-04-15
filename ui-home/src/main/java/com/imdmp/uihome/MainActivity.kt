package com.imdmp.uihome

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sampleVideoListItem = VideoListItem(
            imageUrl = "",
            title = "",
            author = "",
            authorImageUrl = null,
            viewCount = 0,
            streamUrl = ""
        )

        val videoList = listOf(
            sampleVideoListItem.copy(title = "Title1", author = "Author1", viewCount = 2L),
            sampleVideoListItem.copy(title = "Title2", author = "Author2", viewCount = 1L),
            sampleVideoListItem.copy(title = "Title3", author = "Author3", viewCount = 5L),
            sampleVideoListItem.copy(title = "Title4", author = "Author4", viewCount = 10L)

        )
        setContent {
            MaterialTheme {
                VideoListScreen(
                    videoList = videoList,
                    videoListScreenActions = VideoListScreenActions.default()
                )
            }
        }
    }
}