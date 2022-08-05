package com.imdmp.videoplayer.player

import android.content.Context
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.testing.TestLifecycleOwner
import androidx.test.annotation.UiThreadTest
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.testutil.TestExoPlayerBuilder
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
    lateinit var videoPlayerScreenCallbacks: com.imdmp.videoplayer.model.VideoPlayerScreenCallbacks

    lateinit var lifecycleOwner: LifecycleOwner

    @Mock
    lateinit var mockedControlsCallback: com.imdmp.videoplayer.controls.ControlsCallback

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
    }
    
    @Test
    fun VideoPlayerScreen_Contrains_LifecycleHandler(){
    }

}
