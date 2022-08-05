package com.imdmp.youtubecompose.features.videolist.model

class DummyDataProvider {


    companion object {
        fun provideData(): List<VideoListItem> {

            val sampleVideoListItem = VideoListItem.default()

            val videoList = listOf(
                sampleVideoListItem.copy(
                    title = "Long title but probably only one line long",
                    author = "Author1",
                    viewCount = 2L,
                    streamUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"
                ),
                sampleVideoListItem.copy(
                    title = "This is a sample of a long title that can probably take two lines long",
                    author = "Author2",
                    viewCount = 1L,
                    streamUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4"
                ),
                sampleVideoListItem.copy(
                    title = "Title3",
                    author = "Author3",
                    viewCount = 5L,
                    streamUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4"
                ),
                sampleVideoListItem.copy(
                    title = "Title4",
                    author = "Author4",
                    viewCount = 10L,
                    streamUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4"
                )

            )

            return videoList
        }
    }
}