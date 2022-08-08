package com.imdmp.youtubecompose.features.navigation.bottombar

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.imdmp.youtubecompose.features.navigation.model.Destination
import com.imdmp.youtubecompose.features.navigation.model.NavigationBarItem.Companion.buildNavigationItems

@Composable
fun BottomNavigationBar(
    modifier: Modifier = Modifier,
    currentDestination: Destination,
    onNavigate: (destination: Destination) -> Unit
) {
    BottomNavigation(modifier = modifier) {
        buildNavigationItems(
            currentDestination = currentDestination,
            onNavigate = onNavigate
        ).forEach { destination ->
            BottomNavigationItem(
                selected = destination.selected,
                icon = { destination.icon() },
                label = { destination.label() },
                onClick = { destination.onClick() }
            )

        }
    }
}