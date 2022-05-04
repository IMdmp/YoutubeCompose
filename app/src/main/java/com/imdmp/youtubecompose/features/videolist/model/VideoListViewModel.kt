package com.imdmp.youtubecompose.features.videolist.model

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imdmp.datarepository.YoutubeRepository
import com.imdmp.uihome.VideoListItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VideoListViewModel @Inject constructor(
    private val youtubeRepository: YoutubeRepository
) :
    ViewModel() {
    val videoList = mutableStateOf<List<VideoListItem>>(listOf())

    fun search(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            videoList.value = youtubeRepository.getYTDataList().ytDataList.map {
                VideoListItem(
                    imageUrl = it.thumbnail,
                    title = it.name,
                    author = it.uploaderName,
                    authorImageUrl = it.uploaderThumbnail,
                    viewCount = it.viewCount,
                    uploadedDate = "",
                    streamUrl = it.url
                )
            }
        }
    }
}
