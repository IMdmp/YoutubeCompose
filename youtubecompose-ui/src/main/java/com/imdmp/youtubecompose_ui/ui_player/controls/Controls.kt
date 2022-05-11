package com.imdmp.youtubecompose_ui.ui_player.controls

import android.view.ContextThemeWrapper
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.constraintlayout.compose.ConstraintLayout
import com.github.rubensousa.previewseekbar.exoplayer.PreviewTimeBar
import com.google.android.exoplayer2.ui.PlayerControlView
import com.imdmp.ui_core.theme.lighterGreyTransparent
import com.imdmp.ui_core.theme.typography
import com.imdmp.youtubecompose_ui.R
import com.imdmp.youtubecompose_ui.ui_player.Tags
import compose.icons.FontAwesomeIcons
import compose.icons.Octicons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.*
import compose.icons.octicons.Gear24

@Composable
fun Controls(
    modifier: Modifier = Modifier,
    controlsCallback: ControlsCallback = ControlsCallback.default(),
    controlState: ControlState = ControlState.PAUSED,
) {
    AndroidView(factory = { context ->
        PlayerControlView(context).apply {

        }
    })
    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
            .background(lighterGreyTransparent)
    ) {
        val (pausePlay, fullScreen, backwardLeft, forwardRight, videoQuality, moreOptions, seekbar, time, minimize) = createRefs()

        IconButton(onClick = { /*TODO*/ },
            modifier = Modifier
                .size(24.dp)
                .constrainAs(minimize) {
                    start.linkTo(parent.start, 16.dp)
                    top.linkTo(parent.top, 8.dp)
                }
        ) {
            Icon(
                imageVector = FontAwesomeIcons.Solid.AngleDown,
                tint = Color.White,
                contentDescription = ""
            )
        }

        Text(
            text = "720P", style = typography.h2,
            color = Color.White,
            modifier = Modifier.constrainAs(videoQuality) {
                top.linkTo(parent.top, 8.dp)
                end.linkTo(moreOptions.start, 16.dp)
            }
        )
        IconButton(onClick = { /*TODO*/ },
            modifier = Modifier
                .size(24.dp)
                .constrainAs(moreOptions) {
                    end.linkTo(parent.end, 16.dp)
                    top.linkTo(parent.top, 8.dp)
                }
        ) {
            Icon(
                imageVector = Octicons.Gear24,
                tint = Color.White,
                contentDescription = ""
            )
        }


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

        AndroidView(factory = { context ->
            val style = R.style.ExoStyledControls_TimeBar
            PreviewTimeBar(ContextThemeWrapper(context, style), null).apply {
            }
        }, modifier = Modifier
            .fillMaxWidth()
            .constrainAs(seekbar) {
                bottom.linkTo(parent.bottom)
            }) {
        }

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
                    bottom.linkTo(parent.bottom, 32.dp)
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
