package com.imdmp.youtubecompose.features.videoplayer.controls

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.*

@Composable
fun Controls(
    modifier: Modifier = Modifier,
    controlsCallback: ControlsCallback = ControlsCallback.default()
) {
    ConstraintLayout(modifier = modifier.fillMaxSize()) {
        val (pausePlay, fullScreen, backwardLeft, forwardRight, timerCurrent, endTime) = createRefs()

        PauseOrPlayIcon(

            modifier = Modifier
                .size(32.dp)
                .constrainAs(pausePlay) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                })

        Icon(
            imageVector = FontAwesomeIcons.Solid.StepBackward,
            tint = Color.White,
            contentDescription = "",
            modifier = Modifier
                .size(24.dp)
                .constrainAs(backwardLeft) {
                    start.linkTo(parent.start)
                    end.linkTo(pausePlay.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
        )

        Icon(
            imageVector = FontAwesomeIcons.Solid.FastForward,
            tint = Color.White,
            contentDescription = "",
            modifier = Modifier
                .size(24.dp)
                .constrainAs(forwardRight) {
                    start.linkTo(pausePlay.end)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
        )



        Icon(
            imageVector = FontAwesomeIcons.Solid.Expand,
            tint = Color.White,
            contentDescription = "fullscreen",
            modifier = Modifier
                .size(16.dp)
                .clickable {
                    controlsCallback.fullScreenClicked()
                }
                .constrainAs(fullScreen) {
                    end.linkTo(parent.end, 8.dp)
                    bottom.linkTo(parent.bottom, 8.dp)
                }
        )
    }
}

@Composable
fun PauseOrPlayIcon(
    type: ControlState = ControlState.PAUSED,
    modifier: Modifier
) {
    when (type) {
        ControlState.PAUSED -> {
            Icon(
                tint = Color.White,
                modifier = modifier,
                imageVector = FontAwesomeIcons.Solid.Play,
                contentDescription = ""
            )
        }
        ControlState.PLAYING -> {
            Icon(
                modifier = modifier,
                imageVector = FontAwesomeIcons.Solid.Pause,
                contentDescription = ""
            )
        }

    }

}

enum class ControlState {
    PAUSED, PLAYING
}

@Preview
@Composable
fun PreviewControls() {
    Controls()
}