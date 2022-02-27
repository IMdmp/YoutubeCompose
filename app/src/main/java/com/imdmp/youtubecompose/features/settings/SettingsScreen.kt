package com.imdmp.youtubecompose.features.settings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.imdmp.youtubecompose.features.settings.model.SettingsState
import com.imdmp.youtubecompose.features.settings.model.SettingsViewModel

@Composable
fun Settings(viewModel: SettingsViewModel) {

    MaterialTheme {
        SettingsScreen(
            state = viewModel.uiState.collectAsState().value,
            onToggleFeedEnabledSettings = viewModel::toggleFeedEnabledSettings
        )

    }
}

@Composable
private fun SettingsScreen(
    modifier: Modifier = Modifier,
    state: SettingsState,
    onToggleFeedEnabledSettings: () -> Unit
) {

    Column(modifier = modifier) {
        TopBar()
        FeedEnabledSettings(
            title = "Enable Feed",
            checked = state.feedEnabled,
            onToggleFeedEnabledSettings = onToggleFeedEnabledSettings
        )

    }
}

@Composable
fun TopBar() {
    TopAppBar(
        backgroundColor = MaterialTheme.colors.surface,
        contentPadding = PaddingValues(start = 12.dp)
    ) {
        Row {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                tint = MaterialTheme.colors.onSurface,
                contentDescription = ""
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "Settings",
                color = MaterialTheme.colors.onSurface,
                fontSize = 18.sp
            )
        }
    }
}

@Composable
fun FeedEnabledSettings(
    modifier: Modifier = Modifier,
    title: String,
    checked: Boolean,
    onToggleFeedEnabledSettings: () -> Unit
) {
    Surface(modifier = modifier) {
        Row(
            modifier = Modifier
                .toggleable(
                    value = checked,
                    onValueChange = { onToggleFeedEnabledSettings() },
                    role = Role.Switch
                )
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = title, modifier = Modifier.weight(1f))
            Switch(
                checked = checked,
                onCheckedChange = null
            )
        }
    }
}