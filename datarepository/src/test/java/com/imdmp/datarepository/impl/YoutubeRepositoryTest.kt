package com.imdmp.datarepository.impl

import com.imdmp.datarepository.KioskInfoConverter
import com.imdmp.datarepository.NewPipeExtractorWrapper
import com.imdmp.datarepository.YoutubeRepository
import com.imdmp.datarepository.model.YTDataItem
import com.imdmp.datarepository.model.YTDataSchema
import net.bytebuddy.utility.RandomString
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.schabi.newpipe.extractor.kiosk.KioskInfo
import kotlin.random.Random

class YoutubeRepositoryTest {


    lateinit var youtubeRepositoryImpl: YoutubeRepository

    @Mock
    lateinit var newPipeExtractorWrapper: NewPipeExtractorWrapper

    @Mock
    lateinit var kioskInfoConverter: KioskInfoConverter

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        youtubeRepositoryImpl = YoutubeRepositoryImpl(newPipeExtractorWrapper, kioskInfoConverter)
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

        whenever(kioskInfoConverter.mapToYtDataList(any())).thenReturn(
            expectedYTDataSchema.ytDataList
        )

        val actualYtDataSchema = youtubeRepositoryImpl.getYTDataList()

        Assert.assertEquals(expectedYTDataSchema, actualYtDataSchema)
    }
}