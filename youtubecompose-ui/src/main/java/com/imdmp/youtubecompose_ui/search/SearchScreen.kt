package com.imdmp.youtubecompose_ui.search

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.imdmp.ui_core.theme.YoutubeComposeTheme
import com.imdmp.ui_core.theme.typography
import com.imdmp.youtubecompose_ui.R
import compose.icons.Octicons
import compose.icons.octicons.ArrowLeft24
import compose.icons.octicons.ArrowUpLeft24
import compose.icons.octicons.Search24
import compose.icons.octicons.X24


@ExperimentalMaterialApi
@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    searchState: SearchState,
    lazyListState: LazyListState = rememberLazyListState(),
    onSearchTextValueChanged: (String) -> Unit = {},
    searchSelected: KeyboardActionScope.(String) -> Unit = {},
    suggestionSelected: (String) -> Unit = {},
) {

    Column(modifier = Modifier.padding(start = 4.dp, end = 4.dp, top = 8.dp)) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp)
                .fillMaxWidth()
        ) {
            IconButton(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .padding(end = 8.dp)
                    .size(24.dp)
                    .align(Alignment.CenterVertically)
            ) {
                Icon(imageVector = Octicons.ArrowLeft24, contentDescription = null)
            }
            CustomTextField(
                modifier = Modifier
                    .weight(0.9f)
                    .clip(RoundedCornerShape(8.dp))
                    ,
                textValue = searchState.searchText,
                onValueChange = onSearchTextValueChanged,
                imeActionSelected = searchSelected
            )

            IconButton(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .padding(start = 8.dp)
                    .size(24.dp)
                    .align(Alignment.CenterVertically)
            ) {
                Icon(imageVector = Octicons.X24, contentDescription = null)
            }
        }
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
            Icon(
                imageVector = Octicons.Search24,
                contentDescription = null
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
            Icon(
                imageVector = Octicons.ArrowUpLeft24,
                contentDescription = null
            )
        }
    }
}

@ExperimentalMaterialApi
@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun CustomTextField(
    modifier: Modifier = Modifier,
    imeAction: ImeAction = ImeAction.Search,
    onValueChange: (String) -> Unit,
    imeActionSelected: KeyboardActionScope.(String) -> Unit = {},
    textValue: String,
) {
    val keyboard = LocalSoftwareKeyboardController.current
    val interactionSource = remember { MutableInteractionSource() }
    YoutubeComposeTheme {
        Surface {
            BasicTextField(
                value = textValue,
                onValueChange = onValueChange,
                modifier = modifier.fillMaxWidth(),
                interactionSource = interactionSource,
                enabled = true,
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    autoCorrect = false,
                    imeAction = imeAction
                ),
                keyboardActions = KeyboardActions(onAny = {
                    imeActionSelected(textValue)
                    keyboard?.hide()
                }),
            ) { innerTextField ->
                TextFieldDefaults.TextFieldDecorationBox(
                    value = textValue,
                    innerTextField = innerTextField,
                    visualTransformation = VisualTransformation.None,
                    singleLine = true,
                    placeholder = {
                        Text(
                            text = stringResource(R.string.search_youtube),
                            style = typography.h3
                        )
                    },
                    enabled = true,
                    interactionSource = interactionSource,
                    contentPadding = PaddingValues(8.dp),
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewSearchText() {
    YoutubeComposeTheme {

        Surface {
            SearchItem(searchText = "quite a long search query that might not fit in exactly one line")
        }
    }


}

@ExperimentalMaterialApi
@Preview
@Composable
fun PreviewSearchScreen() {
    val searchState = SearchState().copy(
        suggestionList = listOf(
            "search text 1",
            "quite a long search query that might not fit in exactly one line"
        )
    )
    MaterialTheme {
        SearchScreen(searchState = searchState)
    }
}
