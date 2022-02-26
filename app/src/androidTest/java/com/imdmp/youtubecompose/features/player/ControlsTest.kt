package com.imdmp.youtubecompose.features.player

import android.content.Context
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.lifecycle.testing.TestLifecycleOwner
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.android.exoplayer2.testutil.TestExoPlayerBuilder
import com.imdmp.youtubecompose.features.player.controls.Controls
import com.imdmp.youtubecompose.features.player.controls.ControlsCallback
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
    lateinit var mockedControlsCallback: ControlsCallback


    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun ControlsFullScreen_OnClick_FulLScreenCallback() {
        composeTestRule.setContent {
            Controls(
                controlsCallback = mockedControlsCallback
            )
        }

        composeTestRule.onNodeWithContentDescription("fullscreen").performClick()

        verify(mockedControlsCallback).fullScreenClicked()
    }
}