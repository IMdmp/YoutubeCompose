import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.imdmp.youtubecompose.features.search.model.SearchViewModel

@Composable
fun SearchScreen(
    searchViewModel: SearchViewModel = hiltViewModel<SearchViewModel>(),
    navController: NavController
) {

//    SearchScreen(
//        searchState = searchViewModel.searchState.collectAsState().value,
//        onSearchTextValueChanged = {
//            searchViewModel.updateSearchText(it)
//            searchViewModel.getSuggestions(it)
//        },
//        searchSelected = {
//            navController.navigate(Destination.VideoList.createRoute(it))
//        },
//        suggestionSelected = {
//            searchViewModel.updateSearchText(it)
//            navController.navigate(Destination.VideoList.createRoute(it))
//        }
//    )

}
