package com.imdmp.youtubecompose.features.player

import android.content.Context
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.test.annotation.UiThreadTest
import androidx.test.core.app.ApplicationProvider
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.testutil.TestExoPlayerBuilder
import com.imdmp.youtubecompose.MainActivity
import com.imdmp.youtubecompose.MainAppScreen
import com.imdmp.youtubecompose.base.Tags
import com.imdmp.youtubecompose.features.navigation.model.Destination
import com.imdmp.youtubecompose.features.splash.SplashViewModel
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

@HiltAndroidTest
class PlaybackTest {

    @get:Rule(order = 0)
    var hiltAndroidRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @BindValue
    var splashViewModel: SplashViewModel = mock()

    @Mock
    lateinit var videoPlayerScreenCallbacks: VideoPlayerScreenCallbacks

    @Mock
    lateinit var mockedNavController: NavController

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
                streamUrl = dummyUrlString,
                navController = mockedNavController,
                videoPlayerScreenCallbacks = videoPlayerScreenCallbacks
            )
        }

        verify(videoPlayerScreenCallbacks).prepareAndPlayVideoPlayer(dummyUrlString)
    }

    @Test
    fun Player_Control_SelectFullScreen_FullScreenDisplay() {
        composeTestRule.setContent {
            val navController = rememberNavController()
            MainAppScreen(navController = navController)
            Playback(
                streamUrl = "",
                videoPlayerScreenCallbacks = videoPlayerScreenCallbacks,
                navController = navController,
                player = exoPlayer
            )
        }


        composeTestRule.onNodeWithTag(Tags.TAG_BUTTON_SET_FULLSCREENVIEW).performClick()
        composeTestRule.onNodeWithTag(Destination.FullScreenView.path).assertExists()

    }
}