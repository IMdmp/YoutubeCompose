package com.imdmp.datarepository.impl

import com.imdmp.datarepository.model.VideoDataInfoSchema
import com.imdmp.datarepository.model.YTDataItem
import net.bytebuddy.utility.RandomString
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.schabi.newpipe.extractor.comments.CommentsInfo
import org.schabi.newpipe.extractor.comments.CommentsInfoItem
import org.schabi.newpipe.extractor.stream.StreamInfo
import org.schabi.newpipe.extractor.stream.StreamInfoItem
import org.schabi.newpipe.extractor.stream.StreamType
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

        val expectedUrl = RandomString.make(3)
        val streamType = StreamType.VIDEO_STREAM
        val expectedName = RandomString.make(3)

        val streamInfo = StreamInfo(0, expectedUrl, "", streamType, "", expectedName, 18)


        val expected = VideoDataInfoSchema(streamUrl = expectedUrl, videoName = expectedName)
        val actual = sut.mapStreamInfoToVideoDataInfoSchema(streamInfo)

        Assert.assertEquals(expected.streamUrl, actual.streamUrl)
        Assert.assertEquals(expected.videoName, actual.videoName)

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