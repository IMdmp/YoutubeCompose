package com.imdmp.youtubecompose.features.videolist.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.imdmp.youtubecompose.features.videolist.VideoListScreenState
import com.imdmp.ytcore.BlackDarkColor2
import com.imdmp.ytcore.YTCoreTheme
import com.imdmp.ytcore.typography
import com.mikepenz.iconics.compose.Image
import com.mikepenz.iconics.typeface.library.googlematerial.GoogleMaterial


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SearchResultBar(
    modifier: Modifier = Modifier,
    searchState: SearchState,
    searchScreenCallbacks: SearchScreenCallbacks,
    screenState: VideoListScreenState,
) {
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current


    LaunchedEffect(key1 = Unit) {
        if (screenState == VideoListScreenState.SEARCH_ON)
            focusRequester.requestFocus()
    }

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .padding(start = 16.dp, end = 16.dp)
            .fillMaxWidth()
    ) {
        IconButton(
            onClick = { searchScreenCallbacks.onBackButtonClicked() },
            modifier = Modifier
                .padding(end = 8.dp)
                .size(24.dp)
                .align(Alignment.CenterVertically)
        ) {
            Image(
                GoogleMaterial.Icon.gmd_arrow_back,
                colorFilter = ColorFilter.tint(BlackDarkColor2),
            )
        }
        CustomTextField(
            modifier = Modifier
                .focusRequester(focusRequester),
            textValue = searchState.searchText,
            customTextFieldCallbacks = searchScreenCallbacks,
            imeActionSelected = {
                searchScreenCallbacks.onSearchClicked(searchState.searchText.text)
                focusManager.clearFocus()

            }
        )
    }
}

interface CustomTextFieldCallbacks {
    fun textBoxClicked()
    fun textBoxCancelClicked()
    fun onSearchTextValueChanged(newValue: TextFieldValue)
}


@ExperimentalMaterialApi
@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun CustomTextField(
    modifier: Modifier = Modifier,
    imeAction: ImeAction = ImeAction.Search,
    customTextFieldCallbacks: CustomTextFieldCallbacks,
    imeActionSelected: KeyboardActionScope.(String) -> Unit = {},
    textValue: TextFieldValue,
) {
    val keyboard = LocalSoftwareKeyboardController.current
    val interactionSource = remember { MutableInteractionSource() }

    val isPressed by interactionSource.collectIsPressedAsState()

    if (isPressed) {
        customTextFieldCallbacks.textBoxClicked()
    }

    val visualTransformation: VisualTransformation = VisualTransformation.None
    val colors: TextFieldColors = TextFieldDefaults.textFieldColors()
    val shape: Shape = MaterialTheme.shapes.small
        .copy(
            bottomEnd = ZeroCornerSize,
            bottomStart = ZeroCornerSize,
            topStart = ZeroCornerSize,
            topEnd = ZeroCornerSize
        )
    val trailingIcon: @Composable (() -> Unit)? = if (textValue.text.isEmpty()) {
        null
    } else {
        {
            Image(
                modifier = Modifier
                    .size(20.dp)
                    .padding(0.dp)
                    .clickable {
                        customTextFieldCallbacks.textBoxCancelClicked()
                    },
                asset = GoogleMaterial.Icon.gmd_close,
                colorFilter = ColorFilter.tint(BlackDarkColor2),
            )
        }
    }

    val contentPadding = if (textValue.text.isEmpty()) {
        PaddingValues(8.dp)
    } else {
        PaddingValues(4.dp)
    }

    YTCoreTheme {
        Surface {
            BasicTextField(
                modifier = modifier
                    .fillMaxWidth()
                    .heightIn(min = 42.dp, max = 42.dp)
                    .background(colors.backgroundColor(true).value, shape),
                value = textValue,
                onValueChange = {
                    customTextFieldCallbacks.onSearchTextValueChanged(it)
                },
                visualTransformation = visualTransformation,
                cursorBrush = SolidColor(Color.Red),
                interactionSource = interactionSource,
                enabled = true,
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    autoCorrect = false,
                    imeAction = imeAction
                ),
                keyboardActions = KeyboardActions(onAny = {
                    imeActionSelected(textValue.text)
                    keyboard?.hide()
                }),
            ) { innerTextField ->
                TextFieldDefaults.TextFieldDecorationBox(
                    value = textValue.text,
                    innerTextField = innerTextField,
                    visualTransformation = visualTransformation,
                    singleLine = true,
                    trailingIcon = trailingIcon,
                    placeholder = {
                        Text(
                            text = "Search Youtube",
                            style = typography.h3
                        )
                    },
                    enabled = true,
                    interactionSource = interactionSource,
                    contentPadding = contentPadding,
                    colors = colors,
                )
            }

        }
    }

}

@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
fun PreviewTextFieldWithIcons() {
    val searchState = SearchState(searchText = TextFieldValue("Sample search text"))

    YTCoreTheme {
        Surface {
            Column(Modifier.fillMaxSize(), Arrangement.SpaceEvenly) {
                CustomTextField(
                    modifier = Modifier,
                    textValue = searchState.searchText,
                    imeActionSelected = { },
                    customTextFieldCallbacks = SearchScreenCallbacks.default()
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewCustomSearchBar() {
    val searchState = SearchState(searchText = TextFieldValue("Sample search text"))
    YTCoreTheme {
        SearchResultBar(
            searchState = searchState,
            searchScreenCallbacks = SearchScreenCallbacks.default(),
            screenState = VideoListScreenState.DEFAULT
        )
    }
}

@Preview
@Composable
fun PreviewCustomSearchBarEmpty() {
    Surface(Modifier.background(Color.Red)) {
        TextField(value = "", onValueChange = {})
    }

}
