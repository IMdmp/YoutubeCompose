package com.imdmp.youtubecompose.features.videolist

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.imdmp.youtubecompose.base.ui.navigation.model.Destination
import com.imdmp.youtubecompose.base.ui.navigation.model.NavigationBarItem.Companion.buildNavigationItems

@Composable
fun BottomNavigationBar(
    modifier: Modifier = Modifier,
    currentDestination: Destination,
    onNavigate: (destination: Destination) -> Unit
) {

//    val navBackStackEntry = navController.currentBackStackEntryAsState()
//
//    val currentDestination by derivedStateOf {
//        Destination.fromString(navBackStackEntry.value?.destination?.route)
//    }
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