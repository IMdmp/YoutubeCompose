package com.imdmp.youtubecompose.features.navigation.model

import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

class NavigationBarItem(
    val selected: Boolean,
    val onClick: () -> Unit,
    val icon: @Composable () -> Unit,
    val label: @Composable () -> Unit
) {

    companion object {
        fun buildNavigationItems(
            currentDestination: Destination,
            onNavigate: (destination: Destination) -> Unit
        ): List<NavigationBarItem> {
            return listOf(
                Destination.VideoList,
                Destination.Search,
                Destination.Profile,
            ).map { destination ->
                NavigationBarItem(
                    selected = currentDestination == destination,
                    label = {
                        Text(text = destination.title)
                    },
                    icon = {
                        destination.icon?.let { image ->
                            Icon(
                                imageVector = image,
                                contentDescription = destination.path
                            )
                        }
                    },
                    onClick = {
                        onNavigate(destination)
                    }
                )
            }
        }
    }
}