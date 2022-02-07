package com.imdmp.youtubecompose.features.home

import android.os.Parcelable
import androidx.annotation.Nullable
import kotlinx.parcelize.Parcelize
import java.io.Serializable


data class DataItem(
    val imageUrl: String,
    val title: String = "",
    val author: String = "",
    val viewCount: Int = 0,
    val streamUrl: String
) : Serializable {
    companion object {
        fun default(): DataItem = DataItem(
            imageUrl = "", title = "", author = "", viewCount = 0, streamUrl = ""

        )
    }
}