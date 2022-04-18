package com.imdmp.ui_player.controls

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.imdmp.ui_player.R
import com.imdmp.ui_player.Tags
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.*

@Composable
fun Controls(
    modifier: Modifier = Modifier,
    controlsCallback: ControlsCallback = ControlsCallback.default(),
    controlState: ControlState = ControlState.PAUSED,
) {
    ConstraintLayout(modifier = modifier.fillMaxSize()) {
        val (pausePlay, fullScreen, backwardLeft, forwardRight, timerCurrent, endTime, progressIndicator) = createRefs()

        PauseOrPlayIcon(
            type = controlState,
            controlsCallback = controlsCallback,
            modifier = Modifier
                .size(32.dp)
                .constrainAs(pausePlay) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
        )

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
    modifier: Modifier,
    controlsCallback: ControlsCallback,
) {
    val imageVector = when (type) {
        ControlState.PAUSED -> {
            FontAwesomeIcons.Solid.Play
        }
        ControlState.PLAYING -> {
            FontAwesomeIcons.Solid.Pause
        }

    }

    IconButton(
        modifier = modifier
            .testTag(Tags.TAG_PAUSE_PLAY_BUTTON),
        onClick = { controlsCallback.pauseOrPlayClicked() })
    {
        Icon(
            tint = Color.White,
            imageVector = imageVector,
            contentDescription = stringResource(R.string.pause)
        )
    }
}

enum class ControlState {
    PAUSED, PLAYING
}

@Preview
@Composable
fun PreviewControls() {
    Controls(controlState = ControlState.PAUSED)
}