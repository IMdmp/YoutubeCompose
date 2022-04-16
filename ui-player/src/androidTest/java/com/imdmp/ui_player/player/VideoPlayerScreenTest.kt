package com.imdmp.ui_player.player

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.testing.TestLifecycleOwner
import androidx.test.annotation.UiThreadTest
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.testutil.TestExoPlayerBuilder
import com.imdmp.ui_player.Tags
import com.imdmp.ui_player.VideoPlayerScreen
import com.imdmp.ui_player.controls.ControlsCallback
import com.imdmp.ui_player.model.VideoPlayerComposeScreenState
import com.imdmp.ui_player.model.VideoPlayerScreenCallbacks
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@RunWith(AndroidJUnit4::class)
class VideoPlayerScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    lateinit var mockedVideoPlayer: ExoPlayer

    @Mock
    lateinit var videoPlayerScreenCallbacks: VideoPlayerScreenCallbacks

    lateinit var lifecycleOwner: LifecycleOwner

    @Mock
    lateinit var mockedControlsCallback: ControlsCallback

    @UiThreadTest
    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        val context: Context = ApplicationProvider.getApplicationContext()

        mockedVideoPlayer = TestExoPlayerBuilder(context).build()
        lifecycleOwner = TestLifecycleOwner()
    }

    @Test
    fun VideoPlayerScreen_Contains_Comments_List() {
        composeTestRule.setContent {

            VideoPlayerScreen(
                player = mockedVideoPlayer,
                state = VideoPlayerComposeScreenState.init(),
                videoPlayerScreenCallbacks =videoPlayerScreenCallbacks,
                lifecycleOwner =lifecycleOwner,
                controlsCallback = mockedControlsCallback
            )
        }

        composeTestRule.onNodeWithTag(
            Tags.TAG_COMMENTS_LIST,
        )
    }
    
    @Test
    fun VideoPlayerScreen_Contrains_LifecycleHandler(){
        composeTestRule.setContent {

            VideoPlayerScreen(
                player = mockedVideoPlayer,
                state = VideoPlayerComposeScreenState.init(),
                videoPlayerScreenCallbacks = videoPlayerScreenCallbacks,
                lifecycleOwner = lifecycleOwner,
                controlsCallback = mockedControlsCallback,
            )
        }

        composeTestRule.onNodeWithTag(
            Tags.TAG_PLAYER_LIFECYCLER_HANDLER
        ).assertIsDisplayed()
    }

}
