package com.imdmp.youtubecompose.features.videolist

import SimpleOutlinedTextFieldSample
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.imdmp.youtubecompose.R
import com.skydoves.landscapist.glide.GlideImage
import com.imdmp.youtubecompose.features.navigation.model.Destination

@Composable
fun VideoListScreen(
    videoListViewModel: VideoListViewModel,
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
    videoListScreenActions: VideoListScreenActions
) {
    val navBackStackEntry = navController.currentBackStackEntryAsState()

    val currentDestination by derivedStateOf {
        Destination.fromString(navBackStackEntry.value?.destination?.route)
    }

    Scaffold(topBar = {
        TopAppBar(
            modifier = Modifier,
            title = {
                Text(text = stringResource(id = R.string.app_name))
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

@Composable
fun Toolbar(toolbarActions: ToolbarActions) {
    Row(
        Modifier
            .padding(8.dp)
            .clickable {
                toolbarActions.searchClicked()
            }) {
        Text("Youtube Compose", fontSize = 22.sp)
        SimpleOutlinedTextFieldSample()
    }
}

interface VideoItemActions {
    fun videoItemSelected(videoListItem: VideoListItem)

    companion object {
        fun default(): VideoItemActions = object : VideoItemActions {
            override fun videoItemSelected(videoListItem: VideoListItem) {
                TODO("Not yet implemented")
            }

        }
    }
}

@Composable
fun VideoItem(item: VideoListItem, videoItemActions: VideoItemActions) {
    ConstraintLayout(modifier = Modifier
        .fillMaxWidth()
        .clickable {
            videoItemActions.videoItemSelected(item)
        }) {
        val (image, authorImage, title, button, subtitle) = createRefs()

        GlideImage(
            imageModel = item.imageUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(200.dp)
                .constrainAs(image) {
                    linkTo(
                        start = parent.start,
                        end = parent.end,
                    )
                    top.linkTo(parent.top)
                    width = Dimension.fillToConstraints
                }
        )
        GlideImage(
            imageModel = item.authorImageUrl ?: "",
            contentDescription = null,
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .constrainAs(authorImage) {
                    start.linkTo(parent.start, margin = 12.dp)
                    top.linkTo(image.bottom, margin = 16.dp)
                    end.linkTo(title.start)
                }
        )
        Text(
            text = item.title,
            style = MaterialTheme.typography.h6.copy(fontSize = 14.sp),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.constrainAs(title) {
                linkTo(
                    start = authorImage.end,
                    startMargin = 16.dp,
                    end = button.start,
                    endMargin = 16.dp
                )
                linkTo(
                    top = authorImage.top,
                    bottom = subtitle.top
                )
                width = Dimension.fillToConstraints
            }
        )
        Text(
            text = "${item.author} . ${item.viewCount}k views . 6 hours ago",
            style = MaterialTheme.typography.subtitle2,
            modifier = Modifier
                .constrainAs(subtitle) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(title.start)
                    width = Dimension.fillToConstraints
                }
                .padding(bottom = 24.dp)
        )
        IconButton(
            onClick = { },
            modifier = Modifier
                .constrainAs(button) {
                    top.linkTo(authorImage.bottom)
                    end.linkTo(parent.end)
                }
        ) {
            Icon(Icons.Default.MoreVert, tint = Color.Gray, contentDescription = null)
        }
    }
}

@Composable
@Preview
fun PreviewVideoItem() {
    val dataItem = VideoListItem.default()
    VideoItem(dataItem, VideoItemActions.default())
}

//@Composable
//@Preview
//fun PreviewListScreen(){
//    ListScreen()
//}