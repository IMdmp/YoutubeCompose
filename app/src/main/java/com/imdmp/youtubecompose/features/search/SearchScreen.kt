import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import com.imdmp.youtubecompose.features.search.SearchScreenActions

@Composable
fun SearchScreen(searchScreenActions: SearchScreenActions?) {
    Column {
        SimpleOutlinedTextFieldSample()
        Button(onClick = {
            searchScreenActions?.onSearchClicked("test")
        }){
            Text(text = "Search")
        }
    }
}

@Composable
fun SimpleOutlinedTextFieldSample() {
    var text by remember { mutableStateOf("") }

    OutlinedTextField(
        value = text,
        onValueChange = { text = it },
        label = { Text("Label") }
    )
}