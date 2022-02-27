import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.imdmp.youtubecompose.features.search.model.SearchState
import com.imdmp.youtubecompose.features.search.model.SearchViewModel
import com.imdmp.youtubecompose.features.ui.navigation.model.Destination
import timber.log.Timber

@Composable
fun SearchScreen(
    searchViewModel: SearchViewModel = hiltViewModel<SearchViewModel>(),
    navController: NavController
) {

    SearchScreen(
        searchViewModel = searchViewModel,
        navController = navController,
        searchState = searchViewModel.searchState.collectAsState(),
        suggestionSelected = {
            searchViewModel.updateSearchText(it)
            navController.navigate(Destination.VideoList.createRoute(it))
        }
    )

}

@Composable
private fun SearchScreen(
    searchViewModel: SearchViewModel,
    navController: NavController,
    searchState: State<SearchState>,
    suggestionSelected: (String) -> Unit,
) {
    LazyColumn {
        item {
            SimpleOutlinedTextFieldSample(
                textValue = searchState.value.searchText,
                onValueChange = {
                    searchViewModel.updateSearchText(it)
                    searchViewModel.getSuggestions(it)
                },
                imeActionSelected = {
                    navController.navigate(Destination.VideoList.createRoute(it))
                }
            )
        }
        Timber.d("search suggestions; ${searchState.value}")
        items(searchState.value.suggestionList) {
            Box(modifier = Modifier
                .fillMaxWidth()
                .selectable(true) {
                    suggestionSelected(it)
                }) {
                Text(it, fontSize = 20.sp)
            }

        }


    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun SimpleOutlinedTextFieldSample(
    imeAction: ImeAction = ImeAction.Search,
    onValueChange: (String) -> Unit,
    imeActionSelected: KeyboardActionScope.(String) -> Unit = {},
    textValue: String,
) {
    val keyboard = LocalSoftwareKeyboardController.current

    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = textValue,
        onValueChange = {
            onValueChange(it)
        },
        keyboardOptions = KeyboardOptions(
            autoCorrect = false,
            imeAction = imeAction
        ),
        keyboardActions = KeyboardActions(onAny = {
            imeActionSelected(textValue)
            keyboard?.hide()
        }),
        label = { Text("Label") }
    )
}