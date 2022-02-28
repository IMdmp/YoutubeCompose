package com.imdmp.youtubecompose.features.videolist.topappbar

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.imdmp.youtubecompose.R
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Brands
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.brands.Youtube
import compose.icons.fontawesomeicons.solid.User

@Composable
fun HomeTopAppBar(
    modifier: Modifier = Modifier,
    toolbarActions: ToolbarActions
) {
    TopAppBar(
        modifier = modifier,
        backgroundColor = MaterialTheme.colors.surface,
        contentColor = MaterialTheme.colors.onSurface,
        navigationIcon = {
            Icon(
                imageVector = FontAwesomeIcons.Brands.Youtube,
                contentDescription = null,
                tint = Color.Red,
                modifier = Modifier
                    .padding(start = 16.dp)
                    .size(32.dp)
            )
        },
        title = {
            Text(text = stringResource(id = R.string.app_name), color = Color.Black)
        },

        actions = {
            IconButton(onClick = {
                toolbarActions.searchClicked()
            }) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "",
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }

            IconButton(onClick = {
                toolbarActions.profileClicked()
            }) {
                Icon(
                    imageVector = FontAwesomeIcons.Solid.User,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(vertical = 4.dp, horizontal = 8.dp)
                        .size(24.dp)
                        .clip(CircleShape)
                )
            }
        }
    )
}

@Preview
@Composable
fun PreviewHomeTopAppBar() {
    HomeTopAppBar(toolbarActions = ToolbarActions.default())
}