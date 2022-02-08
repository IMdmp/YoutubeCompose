package com.imdmp.youtubecompose.features.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imdmp.youtubecompose.player.PlayerDataSource
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.schabi.newpipe.extractor.NewPipe
import org.schabi.newpipe.extractor.kiosk.KioskInfo
import org.schabi.newpipe.extractor.search.SearchInfo
import org.schabi.newpipe.extractor.services.youtube.YoutubeService
import org.schabi.newpipe.extractor.stream.StreamInfo
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeListViewModel @Inject constructor() : ViewModel(),ListScreenActions {
    val videoList = MutableLiveData<List<DataItem>>()

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
                    info.relatedItems.map { streamInfoItem ->
                        DataItem(
                            imageUrl = streamInfoItem.thumbnailUrl,
                            title = streamInfoItem.name,
                            author = streamInfoItem.uploaderName,
                            viewCount = streamInfoItem.viewCount.toInt(), streamInfoItem.url
                        )
                    }
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
                    res.relatedItems.map { streamInfoItem ->
                        DataItem(
                            imageUrl = streamInfoItem.thumbnailUrl,
                            title = streamInfoItem.name,
                            author = "",
                            viewCount = 0, streamInfoItem.url
                        )
                    }
            }

            Timber.d("check. $res")
        }
    }

    override fun videoItemSelected(dataItem: DataItem) {
        TODO("Not yet implemented")
    }

    override fun searchClicked() {
        TODO("Not yet implemented")
    }
}