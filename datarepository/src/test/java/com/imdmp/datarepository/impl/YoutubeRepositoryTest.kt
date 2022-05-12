package com.imdmp.datarepository.impl

import app.cash.turbine.test
import com.imdmp.datarepository.NewPipeDataModelConverter
import com.imdmp.datarepository.NewPipeExtractorWrapper
import com.imdmp.datarepository.YoutubeRepository
import com.imdmp.datarepository.model.VideoDataCommentSchema
import com.imdmp.datarepository.model.VideoDataInfoSchema
import com.imdmp.datarepository.model.YTDataItem
import com.imdmp.datarepository.model.YTDataSchema
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import net.bytebuddy.utility.RandomString
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.schabi.newpipe.extractor.comments.CommentsInfo
import org.schabi.newpipe.extractor.kiosk.KioskInfo
import org.schabi.newpipe.extractor.stream.StreamInfo
import kotlin.random.Random

@ExperimentalCoroutinesApi
class YoutubeRepositoryTest {


    lateinit var youtubeRepository: YoutubeRepository

    @Mock
    lateinit var newPipeExtractorWrapper: NewPipeExtractorWrapper

    @Mock
    lateinit var newPipeDataModelConverter: NewPipeDataModelConverter

    private val testDispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        youtubeRepository =
            YoutubeRepositoryImpl(newPipeExtractorWrapper, newPipeDataModelConverter)
    }

    @Test
    fun `getYTDataList returns YTDataSchema`() {

        val expectedYTDataItem = YTDataItem(
            url = RandomString.make(3),
            thumbnail = RandomString.make(3),
            name = RandomString.make(3),
            uploaderThumbnail = null,
            uploaderName = RandomString.make(3),
            viewCount = Random.nextLong(4)
        )

        val expectedYTDataSchema = YTDataSchema(
            ytDataList = listOf(expectedYTDataItem)
        )
        val kioskInfo = mock<KioskInfo>()

        whenever(newPipeExtractorWrapper.getInfo()).thenReturn(
            kioskInfo
        )

        whenever(newPipeDataModelConverter.mapStreamInfoItemListToYtDataList(any())).thenReturn(
            expectedYTDataSchema.ytDataList
        )

        testDispatcher.runBlockingTest {
            val actualYtDataSchema = youtubeRepository.getYTDataList()
            Assert.assertEquals(expectedYTDataSchema, actualYtDataSchema)
        }
    }

    @Test
    fun `getVideoDataInfo returns VideoDataInfoSchema`() {

        val expected = VideoDataInfoSchema(
            title = RandomString.make(3),
            views = 0,
            uploadDate = "",
            likeCount = 0,
            uploaderName = "",
            uploaderProfilePicUrl = "",
            subscriberCount = 0,
            videoDescription = "",
            streamList = listOf()
        )

        val dummyUrl = RandomString.make(3)
        val mockStreamInfo = mock<StreamInfo>()

        whenever(newPipeExtractorWrapper.getVideoData(dummyUrl)).thenReturn(mockStreamInfo)

        whenever(newPipeDataModelConverter.mapStreamInfoToVideoDataInfoSchema(mockStreamInfo)).thenReturn(
            expected
        )

        testDispatcher.runBlockingTest {
            val actual = youtubeRepository.getVideoDataInfo(dummyUrl)

            Assert.assertEquals(expected, actual)
        }

    }

    @Test
    fun `getVideoDataComments returns VideoDataCommentSchema`() {
        val expected = listOf(
            VideoDataCommentSchema(
                name = RandomString.make(3),
                comment = RandomString.make(3),
                profilePicUrl = RandomString.make(3)
            )
        )

        val dummyUrl = RandomString.make(3)
        val mockCommentsInfo = mock<CommentsInfo>()

        whenever(newPipeExtractorWrapper.getComments(dummyUrl))
            .thenReturn(mockCommentsInfo)
        whenever(
            newPipeDataModelConverter.mapCommentsInfoToVideoDataCommentSchemaList(
                mockCommentsInfo
            )
        )
            .thenReturn(expected)

        runTest{
            val actual = youtubeRepository.getVideoDataComments(dummyUrl)

            Assert.assertEquals(expected, actual)
        }
    }

    @Test
    fun `testSearchAutoSuggestion test flow`() {
        val dummyQuery = RandomString.make(4)

        val expectedList = listOf(RandomString.make(3), RandomString.make(5), RandomString.make(2))

        whenever(newPipeExtractorWrapper.getSearchSuggestions(dummyQuery)).thenReturn(
            expectedList as MutableList<String>
        )

        runTest {
            youtubeRepository.searchAutoSuggestion(dummyQuery).test {
                expectedList.forEach {
                    Assert.assertEquals(awaitItem(),it)
                }
                awaitComplete()
            }
        }
    }
}
