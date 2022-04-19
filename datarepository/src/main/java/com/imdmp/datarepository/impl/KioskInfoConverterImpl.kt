package com.imdmp.datarepository.impl

import com.imdmp.datarepository.KioskInfoConverter
import com.imdmp.datarepository.model.YTDataItem
import org.schabi.newpipe.extractor.InfoItem
import org.schabi.newpipe.extractor.channel.ChannelInfoItem
import org.schabi.newpipe.extractor.playlist.PlaylistInfoItem
import org.schabi.newpipe.extractor.stream.StreamInfoItem

class KioskInfoConverterImpl : KioskInfoConverter {
    override fun mapToYtDataList(relatedItems: MutableList<StreamInfoItem>): List<YTDataItem> {

        return relatedItems.mapNotNull { infoItem ->
            when (infoItem.infoType) {
                InfoItem.InfoType.STREAM -> {
                    infoItem as StreamInfoItem
                    YTDataItem(
                        thumbnail = infoItem.thumbnailUrl,
                        name = infoItem.name,
                        uploaderName = infoItem.uploaderName,
                        uploaderThumbnail = infoItem.uploaderAvatarUrl,
                        viewCount = infoItem.viewCount,
                        url = infoItem.url
                    )
                }
                InfoItem.InfoType.PLAYLIST -> {
                    infoItem as PlaylistInfoItem
                    //todo
                    null
                }
                InfoItem.InfoType.CHANNEL -> {
                    infoItem as ChannelInfoItem
                    //todo
                    null
                }
                InfoItem.InfoType.COMMENT -> {
                    //todo

                    null
                }

                else -> {
                    requireNotNull(infoItem.infoType)
                    { "info item info type is null. Please make sure it's set!" }
                    //todo

                    null
                }
            }
        }
    }
}