package com.imdmp.youtubecompose.features.player

import android.content.Context
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.testing.TestLifecycleOwner
import androidx.navigation.NavController
import androidx.test.annotation.UiThreadTest
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.testutil.TestExoPlayerBuilder
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.Spy
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

@RunWith(AndroidJUnit4::class)
class VideoPlayerScreenLifecycleTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Mock
    lateinit var exoPlayer: ExoPlayer

    @UiThreadTest
    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun Exoplayer_Pause_When_Lifecycle_OnPause() {
        val state = PlayerStatus.PLAYING
        val testLifecycleOwner = TestLifecycleOwner()

        testLifecycleOwner.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE)

        composeTestRule.setContent {
            HandleLifecycleChanges(
                state = state,
                lifecycleOwner = testLifecycleOwner,
                exoPlayer = exoPlayer
            )
        }

        verify(exoPlayer).pause()

    }

    @Test
    fun Exoplayer_Play_When_Lifecycle_OnResume() {
        val state = PlayerStatus.PLAYING
        val testLifecycleOwner = TestLifecycleOwner()

        testLifecycleOwner.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)

        composeTestRule.setContent {
            HandleLifecycleChanges(
                state = state,
                lifecycleOwner = testLifecycleOwner,
                exoPlayer = exoPlayer
            )
        }

        verify(exoPlayer).play()
    }
}