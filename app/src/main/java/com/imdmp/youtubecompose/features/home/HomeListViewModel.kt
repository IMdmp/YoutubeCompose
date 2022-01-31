package com.imdmp.youtubecompose.features.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imdmp.youtubecompose.player.PlayerDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.schabi.newpipe.extractor.NewPipe
import org.schabi.newpipe.extractor.kiosk.KioskInfo
import org.schabi.newpipe.extractor.services.youtube.YoutubeService
import org.schabi.newpipe.extractor.stream.StreamInfo
import timber.log.Timber

class HomeListViewModel : ViewModel() {
    val videoList = MutableLiveData<List<DataItem>>()

    fun fetchVideoList() {
        viewModelScope.launch(Dispatchers.IO) {
            val info = KioskInfo.getInfo(YoutubeService(0), "https://www.youtube.com/feed/trending")


            withContext(Dispatchers.Main) {
                videoList.value =
                    info.relatedItems.map { streamInfoItem ->
                        DataItem(
                            imageUrl = streamInfoItem.thumbnailUrl,
                            title = streamInfoItem.name,
                            author = streamInfoItem.uploaderName,
                            viewCount = streamInfoItem.viewCount.toInt()
                        )
                    }
            }
        }
    }
}