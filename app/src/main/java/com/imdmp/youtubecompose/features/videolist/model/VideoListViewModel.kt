package com.imdmp.youtubecompose.features.videolist.model

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imdmp.datarepository.YoutubeRepository
import com.imdmp.youtubecompose.base.mapToVideoListItem
import com.imdmp.youtubecompose_ui.uihome.VideoListItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VideoListViewModel @Inject constructor(
    private val youtubeRepository: YoutubeRepository
) :
    ViewModel() {
    val query = mutableStateOf("")
    val videoList = mutableStateOf<List<VideoListItem>>(listOf())

    fun updateCurrentQuery(query: String) {
        this.query.value = query
    }

    fun retrieveVideoList() {
        viewModelScope.launch(Dispatchers.IO) {
            videoList.value = if (query.value.isNotEmpty()) {
                youtubeRepository.search(query.value).mapToVideoListItem()
            } else {
                youtubeRepository.getYTDataList().mapToVideoListItem()
            }
        }
    }
}

