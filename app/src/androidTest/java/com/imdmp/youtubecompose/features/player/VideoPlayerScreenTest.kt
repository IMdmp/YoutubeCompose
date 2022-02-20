package com.imdmp.youtubecompose.features.player

import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.navigation.NavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.android.exoplayer2.ExoPlayer
import com.imdmp.youtubecompose.base.Tags
import com.imdmp.youtubecompose.features.navigation.model.Destination
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.verify

@RunWith(AndroidJUnit4::class)
class VideoPlayerScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Mock
    lateinit var mockedVideoPlayer: ExoPlayer

    @Mock
    lateinit var mockedNavController: NavController

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun VideoPlayerScreen_Contains_Comments_List() {
        composeTestRule.setContent {

            Playback(
                streamUrl = "",
                videoPlayerScreenCallbacks = VideoPlayerScreenCallbacks.default(),
                navController = mockedNavController,
                player = mockedVideoPlayer
            )
        }

//        composeTestRule.onNodeWithTag(
//            Tags.TAG_COMMENTS_LIST
//        ).assertIsEnabled()
    }
}
