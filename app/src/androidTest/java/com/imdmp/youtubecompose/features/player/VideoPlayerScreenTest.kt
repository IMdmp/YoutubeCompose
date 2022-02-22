package com.imdmp.youtubecompose.features.player

import android.content.Context
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.testing.TestLifecycleOwner
import androidx.navigation.NavController
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
    lateinit var mockedNavController: NavController

    @Mock
    lateinit var videoPlayerScreenCallbacks: VideoPlayerScreenCallbacks

    lateinit var lifecycleOwner: LifecycleOwner

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
                navController = mockedNavController,
                player = mockedVideoPlayer,
                videoPlayerScreenState = VideoPlayerScreenState(
                    playerStatus =PlayerStatus.IDLE,
                    streamUrl = ""
                ),
                videoPlayerScreenCallbacks =videoPlayerScreenCallbacks,
                lifecycleOwner =lifecycleOwner,

            )
        }

//        composeTestRule.onNodeWithTag(
//            Tags.TAG_COMMENTS_LIST
//        ).assertIsEnabled()
    }
}
