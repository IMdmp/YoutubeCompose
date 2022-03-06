import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.imdmp.youtubecompose.base.ui.navigation.model.Destination
import com.imdmp.youtubecompose.base.ui.theme.YoutubeComposeTheme
import com.imdmp.youtubecompose.features.search.model.SearchState
import com.imdmp.youtubecompose.features.search.model.SearchViewModel
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Regular
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.regular.ArrowAltCircleUp
import compose.icons.fontawesomeicons.regular.TimesCircle
import compose.icons.fontawesomeicons.solid.ArrowLeft

@Composable
fun SearchScreen(
    searchViewModel: SearchViewModel = hiltViewModel<SearchViewModel>(),
    navController: NavController
) {

    SearchScreen(
        searchState = searchViewModel.searchState.collectAsState().value,
        onSearchTextValueChanged = {
            searchViewModel.updateSearchText(it)
            searchViewModel.getSuggestions(it)
        },
        searchSelected = {
            navController.navigate(Destination.VideoList.createRoute(it))
        },
        suggestionSelected = {
            searchViewModel.updateSearchText(it)
            navController.navigate(Destination.VideoList.createRoute(it))
        }
    )

}

@Composable
private fun SearchScreen(
    modifier: Modifier = Modifier,
    searchState: SearchState,
    lazyListState: LazyListState = rememberLazyListState(),
    onSearchTextValueChanged: (String) -> Unit = {},
    searchSelected: KeyboardActionScope.(String) -> Unit = {},
    suggestionSelected: (String) -> Unit = {},
) {

    Column(modifier = Modifier.padding(start = 4.dp, end = 4.dp)) {

        Row(horizontalArrangement = Arrangement.Start, modifier = Modifier.padding(4.dp)) {
            IconButton(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.CenterVertically)
            ) {
                Icon(imageVector = FontAwesomeIcons.Solid.ArrowLeft, contentDescription = null)
            }
            CustomTextField(
                textValue = searchState.searchText,
                onValueChange = onSearchTextValueChanged,
                imeActionSelected = searchSelected
            )
        }
        LazyColumn(
            modifier = modifier,
            state = lazyListState
        ) {
            items(searchState.suggestionList) {
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .selectable(true) {
                        suggestionSelected(it)
                    }) {
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(
                                modifier = Modifier.size(16.dp),
                                imageVector = FontAwesomeIcons.Regular.TimesCircle,
                                contentDescription = null
                            )
                        }
                        Text(
                            text = it,
                            fontSize = 20.sp,
                            style = MaterialTheme.typography.h2
                        )
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(
                                modifier = Modifier.size(16.dp),
                                imageVector = FontAwesomeIcons.Regular.ArrowAltCircleUp,
                                contentDescription = null
                            )
                        }
                    }
                }

            }


        }
    }
}

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

    TextField(
        modifier = modifier.fillMaxWidth(),
        value = textValue,
        onValueChange = {
            onValueChange(it)
        },
        placeholder = { Text(text = "Search",style = MaterialTheme.typography.h3) },
        keyboardOptions = KeyboardOptions(
            autoCorrect = false,
            imeAction = imeAction
        ),
        keyboardActions = KeyboardActions(onAny = {
            imeActionSelected(textValue)
            keyboard?.hide()
        }),
        colors = TextFieldDefaults.textFieldColors(

        )

    )
}

@Preview
@Composable
fun PreviewSearchScreen() {
    val searchState = SearchState().copy(
        suggestionList = listOf(
            "search text 1",
            "quite a long search query that might not fit in exactly one line"
        )
    )
    YoutubeComposeTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            SearchScreen(searchState = searchState)
        }
    }
}