package com.imdmp.youtubecompose_ui.ui_player.player

import android.content.Context
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.annotation.UiThreadTest
import androidx.test.core.app.ApplicationProvider
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.testutil.TestExoPlayerBuilder
import com.imdmp.youtubecompose_ui.ui_player.model.VideoPlayerScreenCallbacks
import com.imdmp.youtubecompose_ui.ui_player.playback.Playback
import com.imdmp.youtubecompose_ui.uihome.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.verify

@HiltAndroidTest
class PlaybackTest {

    @get:Rule(order = 0)
    var hiltAndroidRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Mock
    lateinit var videoPlayerScreenCallbacks: VideoPlayerScreenCallbacks

    private lateinit var exoPlayer: ExoPlayer

    private lateinit var context: Context

    @UiThreadTest
    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        context = ApplicationProvider.getApplicationContext()
        exoPlayer = TestExoPlayerBuilder(context).build()

        hiltAndroidRule.inject()
    }

    @Test
    fun Init_And_PlayVideo_When_Playback_Init() {
        val dummyUrlString = "www.sampleurl"
        composeTestRule.setContent {
            Playback(
                player = exoPlayer,
                playerScreenCallbacks = videoPlayerScreenCallbacks
            )
        }

        verify(videoPlayerScreenCallbacks).prepareAndPlayVideoPlayer(dummyUrlString)
    }
}
