package com.imdmp.youtubecompose.features.player

import androidx.lifecycle.ViewModel
import com.imdmp.youtubecompose.usecases.GetVideoStreamUrlUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class VideoPlayerViewModel @Inject constructor(
    getVideoStreamUrlUseCase: GetVideoStreamUrlUseCase
): ViewModel() {
}