package com.imdmp.youtubecompose.features.videoplayer.comments

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.imdmp.youtubecompose.base.Tags
import com.imdmp.youtubecompose.features.videoplayer.model.VideoPlayerScreenState

@Composable
fun Comments(modifier: Modifier = Modifier, videoPlayerScreenState: VideoPlayerScreenState) {
    Surface() {
        LazyColumn(modifier = modifier.testTag(Tags.TAG_COMMENTS_LIST)) {
            items(videoPlayerScreenState.commentList) {
                Text(text = it)
            }
        }
    }
}
