package com.imdmp.youtubecompose_ui

import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.compose.Visibility
import com.imdmp.youtubecompose_ui.ui_player.*

fun videoPlayerScreenConstraints(): ConstraintSet {

    return ConstraintSet {
        val videoPlayer = createRefFor(VIDEO_PLAYER)
        val progressIndicator = createRefFor(PROGRESS_INDICATOR)
        val titleBar = createRefFor(TITLE_BAR)
        val iconActionBar = createRefFor(ICON_ACTION_BAR)
        val videoAuthorInfoBar = createRefFor(VIDEO_AUTHOR_INFO_BAR)
        val commentSeparatorLine = createRefFor(COMMENT_SEPARATOR_LINE)
        val commentRow = createRefFor(COMMENT_ROW)
        val comments = createRefFor(COMMENTS)
        val surface = createRefFor(SURFACE)
        val closeButton = createRefFor(CLOSE_BUTTON)
        val pausePlayButton = createRefFor(PAUSE_PLAY_BUTTON)

        constrain(surface) {
            height = Dimension.matchParent
            width = Dimension.matchParent
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }

        constrain(videoPlayer) {
            start.linkTo(parent.start)
            top.linkTo(parent.top)
        }
        constrain(progressIndicator) {
            start.linkTo(videoPlayer.start)
            end.linkTo(videoPlayer.end)
            top.linkTo(videoPlayer.top)
            bottom.linkTo(videoPlayer.bottom)
        }

        constrain(titleBar) {
            top.linkTo(videoPlayer.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            width = Dimension.fillToConstraints
        }

        constrain(videoAuthorInfoBar) {
            top.linkTo(titleBar.bottom)
        }

        constrain(iconActionBar) {
            top.linkTo(videoAuthorInfoBar.bottom)
        }

        constrain(commentSeparatorLine) {
            top.linkTo(iconActionBar.bottom, 8.dp)

        }

        constrain(commentRow) {
            top.linkTo(commentSeparatorLine.bottom, 8.dp)
        }
        constrain(
            comments
        ) {
            top.linkTo(commentRow.bottom, 8.dp)
        }

        constrain(pausePlayButton) {
            visibility = Visibility.Gone
        }
        constrain(closeButton) {
            visibility = Visibility.Gone
        }
    }

}

fun collapseVideoPlayerScreenConstraints(): ConstraintSet {
    return ConstraintSet {
        val videoPlayer = createRefFor(VIDEO_PLAYER)
        val progressIndicator = createRefFor(PROGRESS_INDICATOR)
        val titleBar = createRefFor(TITLE_BAR)
        val iconActionBar = createRefFor(ICON_ACTION_BAR)
        val videoAuthorInfoBar = createRefFor(VIDEO_AUTHOR_INFO_BAR)
        val commentSeparatorLine = createRefFor(COMMENT_SEPARATOR_LINE)
        val commentRow = createRefFor(COMMENT_ROW)
        val comments = createRefFor(COMMENTS)
        val surface = createRefFor(SURFACE)
        val closeButton = createRefFor(CLOSE_BUTTON)
        val pausePlayButton = createRefFor(PAUSE_PLAY_BUTTON)

        constrain(surface) {
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            bottom.linkTo(parent.bottom)
            height = Dimension.preferredValue(100.dp)
            width = Dimension.matchParent

        }

        constrain(videoPlayer) {
            start.linkTo(parent.start)
            bottom.linkTo(parent.bottom)
            height = Dimension.preferredValue(100.dp)
        }

        constrain(titleBar) {
            start.linkTo(videoPlayer.end, 2.dp)
            end.linkTo(pausePlayButton.start, 2.dp)
            top.linkTo(surface.top)
            bottom.linkTo(surface.bottom)
            width = Dimension.fillToConstraints
        }

        constrain(pausePlayButton) {
            start.linkTo(titleBar.end)
            end.linkTo(closeButton.start)
            top.linkTo(surface.top)
            bottom.linkTo(surface.bottom)
            height = Dimension.preferredValue(16.dp)
            width = Dimension.preferredValue(16.dp)

        }

        constrain(closeButton) {
            start.linkTo(pausePlayButton.end)
            end.linkTo(parent.end)
            top.linkTo(surface.top)
            bottom.linkTo(surface.bottom)
            height = Dimension.preferredValue(16.dp)
            width = Dimension.preferredValue(16.dp)

        }

        constrain(progressIndicator) {
            visibility = Visibility.Gone
            bottom.linkTo(parent.bottom)
        }
        constrain(iconActionBar) {
            top.linkTo(videoAuthorInfoBar.bottom)
            visibility = Visibility.Gone
        }
        constrain(videoAuthorInfoBar) {
            top.linkTo(titleBar.bottom)
            visibility = Visibility.Gone
        }
        constrain(commentSeparatorLine) {
            top.linkTo(iconActionBar.bottom, 8.dp)
            visibility = Visibility.Gone
        }
        constrain(commentRow) {
            top.linkTo(commentSeparatorLine.bottom, 8.dp)
            visibility = Visibility.Gone
        }
        constrain(comments) {
            top.linkTo(commentRow.bottom, 8.dp)
            visibility = Visibility.Gone
        }
    }
}
