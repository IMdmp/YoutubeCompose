package com.imdmp.youtubecompose.features.videolist.search

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.imdmp.youtubecompose.features.videolist.VideoListViewModel
import com.imdmp.ytcore.YTCoreTheme
import com.imdmp.ytcore.typography
import com.mikepenz.iconics.compose.Image
import com.mikepenz.iconics.typeface.library.googlematerial.GoogleMaterial

@ExperimentalMaterialApi
@Composable
fun SearchScreenViewModel(modifier: Modifier = Modifier, videoListViewModel: VideoListViewModel) {
    SearchScreen(
        modifier = modifier,
        searchState = videoListViewModel.searchState(),
        suggestionSelected = {},
        searchScreenCallbacks = videoListViewModel.searchScreenCallbacks()
    )
}


@ExperimentalMaterialApi
@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    searchState: SearchState,
    lazyListState: LazyListState = rememberLazyListState(),
    suggestionSelected: (String) -> Unit,
    searchScreenCallbacks: SearchScreenCallbacks = SearchScreenCallbacks.default(),
) {

    Column(modifier = Modifier.padding(start = 4.dp, end = 4.dp, top = 8.dp)) {

        SearchResultBar(modifier, searchState, searchScreenCallbacks = searchScreenCallbacks)
        LazyColumn(
            modifier = modifier.padding(top = 4.dp),
            state = lazyListState
        ) {
            items(searchState.suggestionList) { text ->
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, end = 8.dp)
                    .selectable(true) {
                        suggestionSelected(text)
                    }) {
                    SearchItem(text)
                }

            }


        }
    }
}

@Composable
fun SearchItem(searchText: String) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 8.dp, bottom = 8.dp)
    ) {
        IconButton(modifier = Modifier
            .padding(end = 16.dp)
            .size(24.dp)
            .align(Alignment.CenterVertically),
            onClick = { /*TODO*/ }) {
            Image(
                GoogleMaterial.Icon.gmd_access_alarm,
                colorFilter = ColorFilter.tint(MaterialTheme.colors.primary),
            )
        }
        Text(
            modifier = Modifier.weight(0.9f),
            text = searchText,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            style = typography.h3
        )
        IconButton(
            modifier = Modifier
                .padding(start = 16.dp)
                .size(24.dp)
                .align(Alignment.CenterVertically),
            onClick = { /*TODO*/ }) {
            Image(
                GoogleMaterial.Icon.gmd_access_alarm,
                colorFilter = ColorFilter.tint(MaterialTheme.colors.primary),
            )
        }
    }
}

@Preview
@Composable
fun PreviewSearchText() {
    YTCoreTheme {

        Surface {
            SearchItem(searchText = "quite a long search query that might not fit in exactly one line")
        }
    }
}

@ExperimentalMaterialApi
@Preview
@Composable
fun PreviewSearchScreen() {

    MaterialTheme {
        SearchScreen(searchState = SearchState.provideSample(), suggestionSelected = {})
    }
}

