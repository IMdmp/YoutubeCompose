package com.imdmp.youtubecompose_ui.ui_player.player

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.verify

@RunWith(AndroidJUnit4::class)
class ControlsTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Mock
    lateinit var mockedControlsCallback: com.imdmp.youtubecompose_ui.ui_player.controls.ControlsCallback


    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun ControlsFullScreen_OnClick_FulLScreenCallback() {
        composeTestRule.setContent {
            com.imdmp.youtubecompose_ui.ui_player.controls.Controls(
                controlsCallback = mockedControlsCallback,

                )
        }

        composeTestRule.onNodeWithContentDescription("fullscreen").performClick()

        verify(mockedControlsCallback).fullScreenClicked()
    }

    @Test
    fun ControlsPause_OnClick_PauseCallback(){
        composeTestRule.setContent {
            com.imdmp.youtubecompose_ui.ui_player.controls.Controls(
                controlsCallback = mockedControlsCallback
            )
        }

        composeTestRule.onNodeWithTag(com.imdmp.youtubecompose_ui.ui_player.Tags.TAG_PAUSE_PLAY_BUTTON)
            .performClick()

        verify(mockedControlsCallback).pauseOrPlayClicked()
    }
}
