package com.imdmp.youtubecompose.features.videolist

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import com.imdmp.youtubecompose.features.navigation.model.Destination
import com.imdmp.youtubecompose.features.navigation.model.NavigationBarItem.Companion.buildNavigationItems

@Composable
fun BottomNavigationBar(
    modifier: Modifier = Modifier,
    currentDestination: Destination,
    onNavigate: (destination: Destination) -> Unit
) {
    BottomNavigation() {
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