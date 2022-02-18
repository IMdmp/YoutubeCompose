package com.imdmp.youtubecompose.features.player

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.android.exoplayer2.ExoPlayer
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

    @Mock
    lateinit var mockedVideoPlayer: ExoPlayer

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun test() {
        composeTestRule.setContent {

            Playback(
                streamUrl = "",
                videoPlayerScreenCallbacks = VideoPlayerScreenCallbacks.default(),
                player = mockedVideoPlayer
            )
        }
    }
}
