package com.imdmp.youtubecompose.features.player

import androidx.compose.ui.test.junit4.createComposeRule
import com.google.android.exoplayer2.ExoPlayer
import org.junit.Assert.*

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.verify

class PlaybackTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Mock
    lateinit var exoPlayer: ExoPlayer

    @Mock
    lateinit var videoPlayerScreenCallbacks: VideoPlayerScreenCallbacks

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)

    }

    @Test
    fun INIT_AND_PLAYVIDEO_WHEN_PLAYBACK_INIT() {
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

    
}