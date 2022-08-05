package com.imdmp.youtubecompose.features.videolist

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.imdmp.videolist.search.SearchResultBar
import com.imdmp.youtubecompose.features.videolist.model.DummyDataProvider
import com.imdmp.youtubecompose.features.videolist.model.VideoListItem
import com.imdmp.youtubecompose.features.videolist.model.VideoListScreenCallbacks
import com.imdmp.youtubecompose.features.videolist.search.SearchScreenViewModel
import com.imdmp.youtubecompose.features.videolist.search.SearchState
import com.imdmp.youtubecompose.features.videolist.topappbar.HomeTopAppBar
import com.imdmp.ytcore.YTCoreTheme

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun VideoListScreen(videoListViewModel: VideoListViewModel) {
    when (videoListViewModel.screenState()) {
        VideoListScreenState.DEFAULT -> {
            LaunchedEffect(key1 = videoListViewModel.searchState().searchText) {
                videoListViewModel.loadQuery()
            }
            VideoListDefaultScreen(videoListViewModel)
        }
        VideoListScreenState.SEARCH_ON -> {
            SearchScreenViewModel(videoListViewModel = videoListViewModel)
        }

    }
}

@Composable
private fun VideoListDefaultScreen(videoListViewModel: VideoListViewModel) {
    VideoListDefaultScreen(
        videoList = videoListViewModel.provideVideoList(),
        videoListScreenActions = videoListViewModel.videoListDefaultScreenCallbacks(),
        searchState = videoListViewModel.searchState()
    )
}


@Composable
private fun VideoListDefaultScreen(
    videoList: List<VideoListItem>?,
    videoListScreenActions: VideoListScreenCallbacks,
    searchState: SearchState = SearchState.idle(),
) {
    Scaffold(
        topBar = {
            if (searchState.searchText.isEmpty()) {
                HomeTopAppBar(
                    toolbarActions = videoListScreenActions
                )
            } else {
                SearchResultBar(
                    modifier = Modifier.padding(top = 8.dp),
                    searchState = searchState,
                    searchScreenCallbacks = videoListScreenActions
                )
            }
        }
    ) {
        if (searchState.searchNetworkState == SearchState.SearchNetworkState.LOADING) {
            LoadingScreen()
        } else if (videoList == null) {
            ErrorScreen()
        } else {
            LazyColumn {
                items(videoList) { data ->
                    VideoItem(item = data, videoListScreenActions)
                }
            }
        }
    }
}

@Composable
fun ErrorScreen() {

}

@Preview
@Composable
fun PreviewVideoListScreen() {

    YTCoreTheme {
        VideoListDefaultScreen(
            videoList = DummyDataProvider.provideData(),
            videoListScreenActions = VideoListScreenCallbacks.default()
        )
    }
}

@Preview
@Composable
fun PreviewVideoListScreenWithSearch() {
    YTCoreTheme {
        VideoListDefaultScreen(
            videoList = DummyDataProvider.provideData(),
            videoListScreenActions = VideoListScreenCallbacks.default(),
            searchState = SearchState.provideSample()
        )

    }
}