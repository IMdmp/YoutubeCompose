package com.imdmp.youtubecompose.features.fullscreenview

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.imdmp.youtubecompose.features.navigation.model.Destination

@Composable
fun FullScreenView() {

    Box(modifier = Modifier.testTag(Destination.FullScreenView.path)) {

    }
}