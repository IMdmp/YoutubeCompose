package com.imdmp.youtubecompose.features.videolist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.imdmp.youtubecompose.R
import com.imdmp.youtubecompose.features.navigation.model.Destination
import com.imdmp.youtubecompose.features.theme.YoutubeComposeTheme
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Brands
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.brands.Youtube
import compose.icons.fontawesomeicons.solid.User

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


    VideoListScreen(videoListState, navController, object : VideoListScreenActions {
        override fun videoItemSelected(videoListItem: VideoListItem) {
            navController.navigate(Destination.Player.createRoute(videoListItem.streamUrl))
        }

        override fun searchClicked() {
            navController.navigate(Destination.Search.path)
        }
    })


}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(
            color = Color.Blue
        )
    }
}

@Composable
private fun VideoListScreen(
    videoList: List<VideoListItem>?,
    navController: NavController,
    videoListScreenActions: VideoListScreenActions,
) {
    val navBackStackEntry = navController.currentBackStackEntryAsState()

    val currentDestination by derivedStateOf {
        Destination.fromString(navBackStackEntry.value?.destination?.route)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier,
                backgroundColor = colors.surface,
                contentColor = colors.onSurface,
                navigationIcon = {
                    Icon(
                        imageVector = FontAwesomeIcons.Brands.Youtube,
                        contentDescription = null,
                        tint = Color.Red,
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .size(32.dp)
                    )
                },
                title = {
                    Text(text = stringResource(id = R.string.app_name), color = Color.Black)
                },

                actions = {
                    IconButton(onClick = {
                        videoListScreenActions.searchClicked()
                    }) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "",
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )
                    }

                    IconButton(onClick = { navController.navigate(Destination.Profile.path) }) {
                        Icon(
                            imageVector = FontAwesomeIcons.Solid.User,
                            contentDescription = null,
                            modifier = Modifier
                                .padding(vertical = 4.dp, horizontal = 8.dp)
                                .size(24.dp)
                                .clip(CircleShape)
                        )
                    }
                }
            )
        },
//        bottomBar = {
//            BottomNavigationBar(
//                currentDestination = currentDestination,
//                onNavigate = { destination ->
//                    navController.navigate(destination.path) {
//                        popUpTo(navController.graph.findStartDestination().id) {
//                            saveState = true
//                        }
//                        launchSingleTop = true
//                        restoreState = true
//                    }
//                })
//        }

    ) {

        if (videoList.isNullOrEmpty()) {
            LoadingScreen()
        } else {
            LazyColumn {
                items(videoList) { data ->
                    VideoItem(item = data, videoListScreenActions)
                }
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