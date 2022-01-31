package com.imdmp.youtubecompose.features.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import com.imdmp.youtubecompose.ui.theme.YoutubeComposeTheme

class HomeListActivity : AppCompatActivity() {

    private val homeListViewModel: HomeListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            YoutubeComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    ListScreen(homeListViewModel)
                }
            }
        }

        homeListViewModel.fetchVideoList()
    }

}