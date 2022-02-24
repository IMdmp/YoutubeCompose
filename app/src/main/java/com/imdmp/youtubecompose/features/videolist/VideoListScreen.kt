package com.imdmp.youtubecompose.features.videolist

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.imdmp.youtubecompose.R
import com.imdmp.youtubecompose.features.navigation.model.Destination
import com.imdmp.youtubecompose.features.theme.YoutubeComposeTheme

@Composable
fun VideoListScreen(
    videoListViewModel: VideoListViewModel = hiltViewModel(),
    navController: NavController,
    query: String
) {
    val videoListState = videoListViewModel.videoList.observeAsState().value

    LaunchedEffect(query) {
        videoListViewModel.search(query)
    }

    videoListState?.let {
        VideoListScreen(it, navController, object : VideoListScreenActions {
            override fun videoItemSelected(videoListItem: VideoListItem) {
                navController.navigate(Destination.Player.createRoute(videoListItem.streamUrl))
            }

            override fun searchClicked() {
                navController.navigate(Destination.Search.path)
            }
        })
    }

}

@Composable
private fun VideoListScreen(
    videoList: List<VideoListItem>,
    navController: NavController,
    videoListScreenActions: VideoListScreenActions,
) {
    val navBackStackEntry = navController.currentBackStackEntryAsState()

    val currentDestination by derivedStateOf {
        Destination.fromString(navBackStackEntry.value?.destination?.route)
    }

    Scaffold(topBar = {
        TopAppBar(
            modifier = Modifier,
            title = {
                Text(text = stringResource(id = R.string.app_name),color = Color.Black)
            },

            actions = {
                IconButton(onClick = {
                    videoListScreenActions.searchClicked()
                }) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = ""
                    )
                }
            }
        )
    },
        bottomBar = {
                BottomNavigationBar(
                    currentDestination = currentDestination,
                    onNavigate = { destination ->
                        navController.navigate(destination.path) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    })
            }

    ) {

        LazyColumn {
            items(videoList) { data ->
                VideoItem(item = data, videoListScreenActions)
            }
        }
    }
}

fun toSearch() {

}

interface ToolbarActions {
    fun searchClicked()

    companion object {
        fun default(): ToolbarActions = object : ToolbarActions {
            override fun searchClicked() {
                TODO("Not yet implemented")
            }

        }
    }
}

@Preview
@Composable
fun PreviewVideoListScreen() {

    val sampleVideoListItem = VideoListItem(
        imageUrl = "", title = "", author = "", authorImageUrl = null, viewCount = 0, streamUrl = ""

    )

    val videoList = listOf(
        sampleVideoListItem.copy(title = "Title1", author = "Author1", viewCount = 2L),
        sampleVideoListItem.copy(title = "Title2", author = "Author2", viewCount = 1L),
        sampleVideoListItem.copy(title = "Title3", author = "Author3", viewCount = 5L),
        sampleVideoListItem.copy(title = "Title4", author = "Author4", viewCount = 10L)

    )
    YoutubeComposeTheme {
        VideoListScreen(
            videoList = videoList,
            navController = rememberNavController(),
            videoListScreenActions = VideoListScreenActions.default()
        )
    }
}