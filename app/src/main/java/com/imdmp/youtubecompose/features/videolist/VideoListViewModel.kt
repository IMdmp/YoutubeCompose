package com.imdmp.youtubecompose.features.videolist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imdmp.youtubecompose.features.videolist.VideoListItem.Companion.mapToDataItems
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.schabi.newpipe.extractor.NewPipe
import org.schabi.newpipe.extractor.kiosk.KioskInfo
import org.schabi.newpipe.extractor.search.SearchInfo
import org.schabi.newpipe.extractor.services.youtube.YoutubeService
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class VideoListViewModel @Inject constructor() : ViewModel() {
    val videoList = MutableLiveData<List<VideoListItem>>()

    init {
        if (true) {
            fetchVideoList()
        } else
            search("")
    }

    fun fetchVideoList() {
        viewModelScope.launch(Dispatchers.IO) {
            val info = KioskInfo.getInfo(YoutubeService(0), "https://www.youtube.com/feed/trending")


            withContext(Dispatchers.Main) {
                videoList.value =
                    info.relatedItems.mapToDataItems()
            }
        }
    }

    fun search(query: String) {
        val contentFilter = listOf("")
        val sortFilter = ""

        viewModelScope.launch(Dispatchers.IO) {
            val res = SearchInfo.getInfo(
                NewPipe.getService(0), NewPipe.getService(0)
                    .searchQHFactory
                    .fromQuery(query, contentFilter, sortFilter)
            )

            withContext(Dispatchers.Main) {
                videoList.value =
                    res.relatedItems.mapToDataItems ()
            }

            Timber.d("check. $res")
        }
    }
}