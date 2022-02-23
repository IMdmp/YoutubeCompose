package com.imdmp.youtubecompose.features.player

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import compose.icons.AllIcons
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Regular
import compose.icons.fontawesomeicons.regular.AddressCard
import compose.icons.fontawesomeicons.regular.WindowMaximize

@Composable
fun Controls(modifier: Modifier = Modifier) {

    ConstraintLayout(modifier = modifier.fillMaxSize()) {
        val (pausePlay,fullScreen,) = createRefs()

        Icon(
            imageVector = FontAwesomeIcons.Regular.WindowMaximize,
            contentDescription = ""
        )
    }
}

@Preview
@Composable
fun PreviewControls() {
    Controls()
}