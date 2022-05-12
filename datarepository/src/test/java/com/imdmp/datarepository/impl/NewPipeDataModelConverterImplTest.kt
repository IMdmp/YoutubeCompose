package com.imdmp.datarepository.impl

import com.imdmp.datarepository.model.YTDataItem
import net.bytebuddy.utility.RandomString
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.schabi.newpipe.extractor.MediaFormat
import org.schabi.newpipe.extractor.comments.CommentsInfo
import org.schabi.newpipe.extractor.comments.CommentsInfoItem
import org.schabi.newpipe.extractor.stream.*
import kotlin.random.Random

class NewPipeDataModelConverterImplTest {

    lateinit var sut: NewPipeDataModelConverterImpl

    @Before
    fun setUp() {
        sut = NewPipeDataModelConverterImpl()
    }

    @Test
    fun `mapToYtDataList converts list of StreamInfo to list of YTDataItem`() {


        val expectedThumbnailUrl = RandomString.make(3)
        val expectedUrl = RandomString.make(3)
        val expectedUploaderName = RandomString.make(3)
        val expectedUploadCount = Random.nextLong()

        val streamInfoItem = StreamInfoItem(1, expectedUrl, "", StreamType.VIDEO_STREAM)
        streamInfoItem.thumbnailUrl = expectedThumbnailUrl
        streamInfoItem.uploaderName = expectedUploaderName
        streamInfoItem.viewCount = expectedUploadCount

        val expectedYTDataItem = YTDataItem(
            url = expectedUrl,
            thumbnail = expectedThumbnailUrl,
            name = "",
            uploaderThumbnail = null,
            uploaderName = expectedUploaderName,
            viewCount = expectedUploadCount
        )

        val actual = sut.mapStreamInfoItemListToYtDataList(mutableListOf(streamInfoItem))

        Assert.assertEquals(listOf(expectedYTDataItem), actual)
    }


    @Test
    fun mapStreamInfoToVideoDataInfoSchema() {

        val expectedVideoStream = listOf(VideoStream("", MediaFormat.M4A, ""))
        val streamType = StreamType.VIDEO_STREAM
        val expectedName = RandomString.make(3)
        val expectedDescription = RandomString.make(3)
        val expectedViewCount = Random.nextLong()
        val expectedLikeCount = Random.nextLong()
        val expectedDate = RandomString.make(10)

        val streamInfo = StreamInfo(0, "", "", streamType, "", expectedName, 18)
        streamInfo.textualUploadDate = expectedDate
        streamInfo.description = Description(expectedDescription, 0)
        streamInfo.viewCount = expectedViewCount
        streamInfo.likeCount = expectedLikeCount
        streamInfo.videoStreams = expectedVideoStream
        val actual = sut.mapStreamInfoToVideoDataInfoSchema(streamInfo)


        Assert.assertEquals(expectedVideoStream, actual.streamList)
        Assert.assertEquals(expectedViewCount, actual.views)
        Assert.assertEquals(expectedDate, actual.uploadDate)
        Assert.assertEquals(expectedLikeCount, actual.likeCount)
        Assert.assertEquals(expectedDescription, actual.videoDescription)
        Assert.assertEquals(expectedName, actual.title)

    }

    @Test
    fun mapToVideoDataCommentSchemaList() {

        val commentsInfo = mock<CommentsInfo>()
        val expectedUrl = RandomString.make(3)
        val expectedName = RandomString.make(3)

        val commentsInfoItem = CommentsInfoItem(0, expectedUrl, expectedName)
        commentsInfoItem.uploaderName = RandomString.make(3)
        commentsInfoItem.commentText = RandomString.make(3)
        commentsInfoItem.uploaderAvatarUrl = RandomString.make(3)

        val expectedList = listOf(commentsInfoItem)
        whenever(commentsInfo.relatedItems).thenReturn(expectedList)

        val actual = sut.mapCommentsInfoToVideoDataCommentSchemaList(commentsInfo)

        Assert.assertEquals(commentsInfoItem.uploaderAvatarUrl, actual.first().profilePicUrl)
        Assert.assertEquals(commentsInfoItem.commentText, actual.first().comment)
        Assert.assertEquals(commentsInfoItem.uploaderName, actual.first().name)


    }
}
