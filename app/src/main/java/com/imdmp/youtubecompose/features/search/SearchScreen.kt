import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import com.imdmp.youtubecompose.features.search.SearchScreenActions

@Composable
fun SearchScreen(searchScreenActions: SearchScreenActions = SearchScreenActions.default()) {
    Column {
        SimpleOutlinedTextFieldSample(

            imeActionSelected = {
                searchScreenActions.onSearchClicked(it)
            }
        )
    }
}

@Composable
fun SimpleOutlinedTextFieldSample(
    imeAction: ImeAction = ImeAction.Search,
    imeActionSelected: KeyboardActionScope.(String) -> Unit = {},
) {
    var textValue by remember { mutableStateOf("") }
    val keyboard = LocalSoftwareKeyboardController.current

    OutlinedTextField(
        value = textValue,
        onValueChange = { textValue = it },
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