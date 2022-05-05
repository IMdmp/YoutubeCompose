import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.imdmp.youtubecompose.base.ui.navigation.model.Destination
import com.imdmp.youtubecompose.features.search.model.SearchViewModel
import com.imdmp.youtubecompose_ui.search.SearchScreen

@ExperimentalMaterialApi
@Composable
fun SearchCombinerScreen(
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
