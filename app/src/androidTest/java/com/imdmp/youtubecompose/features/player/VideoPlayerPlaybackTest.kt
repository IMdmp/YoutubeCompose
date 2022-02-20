package com.imdmp.youtubecompose.features.player

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.testing.TestLifecycleOwner
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.android.exoplayer2.ExoPlayer
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.verify

@RunWith(AndroidJUnit4::class)
class VideoPlayerPlaybackTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Mock
    lateinit var exoPlayer: ExoPlayer

    lateinit var testLifecycleOwner: TestLifecycleOwner

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        testLifecycleOwner = TestLifecycleOwner()
    }

    @Test
    fun Exoplayer_Pause_When_Lifecycle_OnPause() {
        testLifecycleOwner.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE)

        verify(exoPlayer).pause()
    }

    @Test
    fun Exoplayer_Play_When_Lifecycle_OnResume(){
        testLifecycleOwner.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)

        verify(exoPlayer).play()
    }
}