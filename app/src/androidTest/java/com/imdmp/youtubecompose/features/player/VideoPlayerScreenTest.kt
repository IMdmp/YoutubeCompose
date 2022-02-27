package com.imdmp.youtubecompose.features.player

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.testing.TestLifecycleOwner
import androidx.navigation.NavController
import androidx.test.annotation.UiThreadTest
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.testutil.TestExoPlayerBuilder
import com.imdmp.youtubecompose.base.Tags
import com.imdmp.youtubecompose.features.videoplayer.VideoPlayerScreen
import com.imdmp.youtubecompose.features.videoplayer.controls.ControlsCallback
import com.imdmp.youtubecompose.features.videoplayer.model.PlayerStatus
import com.imdmp.youtubecompose.features.videoplayer.model.VideoPlayerScreenCallbacks
import com.imdmp.youtubecompose.features.videoplayer.model.VideoPlayerScreenState
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
    lateinit var mockedNavController: NavController

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
                videoPlayerScreenState = VideoPlayerScreenState(
                    playerStatus = PlayerStatus.IDLE,
                    streamUrl = ""
                ),
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
                videoPlayerScreenState = VideoPlayerScreenState(
                    playerStatus = PlayerStatus.IDLE,
                    streamUrl = ""
                ),
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
