package com.imdmp.youtubecompose.features.player

import android.content.Context
import androidx.annotation.UiThread
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.test.annotation.UiThreadTest
import androidx.test.core.app.ApplicationProvider
import androidx.test.platform.app.InstrumentationRegistry
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.testutil.TestExoPlayerBuilder
import com.imdmp.youtubecompose.base.Tags
import com.imdmp.youtubecompose.base.Tags.TAG_FULLSCREENVIEW
import com.imdmp.youtubecompose.features.navigation.model.Destination
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class PlaybackTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Mock
    lateinit var videoPlayerScreenCallbacks: VideoPlayerScreenCallbacks

    @Mock
    lateinit var mockedNavController: NavController

    lateinit var exoPlayer: ExoPlayer

    lateinit var context: Context

    @UiThreadTest
    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        context = ApplicationProvider.getApplicationContext()
        exoPlayer = TestExoPlayerBuilder(context).build()
    }

    @Test
    fun Init_And_PlayVideo_When_Playback_Init() {
        val dummyUrlString = "www.sampleurl"
        composeTestRule.setContent {
            Playback(
                player = exoPlayer,
                streamUrl = dummyUrlString,
                navController = mockedNavController,
                videoPlayerScreenCallbacks = videoPlayerScreenCallbacks
            )
        }

        verify(videoPlayerScreenCallbacks).prepareAndPlayVideoPlayer(dummyUrlString)
    }

    @Test
    fun Player_Control_SelectFullScreen_FullScreenDisplay() {
        videoPlayerScreenCallbacks = mock()
//
//        composeTestRule.setContent {
//            Playback(
//                player = exoPlayer,
//                streamUrl = "",
//                videoPlayerScreenCallbacks = videoPlayerScreenCallbacks
//            )
//        }
//
//        videoPlayerScreenCallbacks.selectFullScreen()
//
//        composeTestRule.onNodeWithTag(
//            TAG_FULLSCREENVIEW
//        ).assertIsEnabled()

        composeTestRule.setContent {
            val navController = rememberNavController()

            Playback(
                streamUrl = "",
                videoPlayerScreenCallbacks = videoPlayerScreenCallbacks,
                navController = navController,
                player = exoPlayer
            )
        }
        composeTestRule.onNodeWithTag(Tags.TAG_BUTTON_SET_FULLSCREENVIEW).performClick()
//        verify(navController).navigate(Destination.FullScreenView.path)


//        composeTestRule.onNodeWithTag(
//            Tags.TAG_COMMENTS_LIST
//        ).assertIsEnabled()
    }
}