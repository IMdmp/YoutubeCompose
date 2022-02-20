package com.imdmp.youtubecompose.features.player

import android.content.Context
import androidx.annotation.UiThread
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.annotation.UiThreadTest
import androidx.test.core.app.ApplicationProvider
import androidx.test.platform.app.InstrumentationRegistry
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.testutil.TestExoPlayerBuilder
import com.imdmp.youtubecompose.base.Tags.TAG_FULLSCREENVIEW
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

    lateinit var exoPlayer: ExoPlayer

    lateinit var context: Context

    @UiThreadTest
    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        context =  ApplicationProvider.getApplicationContext()
        exoPlayer = TestExoPlayerBuilder(context).build()
    }

    @Test
    fun Init_And_PlayVideo_When_Playback_Init() {
        val dummyUrlString = "www.sampleurl"
        composeTestRule.setContent {
            Playback(
                player = exoPlayer,
                streamUrl = "",
                videoPlayerScreenCallbacks = videoPlayerScreenCallbacks
            )
        }

        verify(videoPlayerScreenCallbacks).prepareAndPlayVideoPlayer(dummyUrlString)
    }

    @Test
    fun Player_Control_SelectFullScreen_FullScreenDisplay() {
        videoPlayerScreenCallbacks = mock()

        composeTestRule.setContent {
            Playback(
                player = exoPlayer,
                streamUrl = "",
                videoPlayerScreenCallbacks = videoPlayerScreenCallbacks
            )
        }

        videoPlayerScreenCallbacks.selectFullScreen()

        composeTestRule.onNodeWithTag(
            TAG_FULLSCREENVIEW
        ).assertIsEnabled()
    }
}