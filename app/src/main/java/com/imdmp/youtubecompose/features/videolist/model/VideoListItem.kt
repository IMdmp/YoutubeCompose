package com.imdmp.youtubecompose.features.videolist.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import org.schabi.newpipe.extractor.InfoItem
import org.schabi.newpipe.extractor.channel.ChannelInfoItem
import org.schabi.newpipe.extractor.playlist.PlaylistInfoItem
import org.schabi.newpipe.extractor.stream.StreamInfoItem


@Parcelize
data class VideoListItem(
    val imageUrl: String,
    val title: String = "",
    val author: String = "",
    val authorImageUrl: String? = null,
    val viewCount: Long = 0,
    val streamUrl: String
) : Parcelable {
    companion object {
        fun default(): VideoListItem = VideoListItem(
            imageUrl = "",
            title = "",
            author = "",
            viewCount = 0,
            streamUrl = "",
            authorImageUrl = ""

        )

        fun List<InfoItem>.mapToDataItems(): List<VideoListItem> {
            return this.map { infoItem ->
                when (infoItem.infoType) {
                    InfoItem.InfoType.STREAM -> {
                        infoItem as StreamInfoItem
                        VideoListItem(
                            imageUrl = infoItem.thumbnailUrl,
                            title = infoItem.name,
                            author = infoItem.uploaderName,
                            authorImageUrl = infoItem.uploaderAvatarUrl,
                            viewCount = infoItem.viewCount,
                            streamUrl = infoItem.url
                        )
                    }
                    InfoItem.InfoType.PLAYLIST -> {
                        infoItem as PlaylistInfoItem

                        VideoListItem(
                            imageUrl = "",
                            title = "",
                            author = "",
                            authorImageUrl = null,
                            viewCount = 0,
                            streamUrl = ""
                        )
                    }
                    InfoItem.InfoType.CHANNEL -> {
                        infoItem as ChannelInfoItem
                        VideoListItem(
                            imageUrl = "",
                            title = "",
                            author = "",
                            authorImageUrl = null,
                            viewCount = 0,
                            streamUrl = ""
                        )
                    }
                    InfoItem.InfoType.COMMENT -> {
                        VideoListItem(
                            imageUrl = "",
                            title = "",
                            author = "",
                            authorImageUrl = null,
                            viewCount = 0,
                            streamUrl = ""
                        )
                    }

                    else -> {
                        requireNotNull(infoItem.infoType)
                        { "info item info type is null. Please make sure it's set!" }

                        VideoListItem(
                            imageUrl = "",
                            title = "",
                            author = "",
                            authorImageUrl = null,
                            viewCount = 0,
                            streamUrl = ""
                        )
                    }
                }


            }

        }
    }


}