package com.imdmp.datarepository.impl

import com.imdmp.datarepository.YoutubeRepository
import org.junit.Before
import org.junit.Test

class YoutubeRepositoryTest {


    lateinit var youtubeRepositoryImpl: YoutubeRepository

    @Before
    fun setUp() {
        youtubeRepositoryImpl = YoutubeRepositoryImpl()
    }

    @Test
    fun `getYTDataList returns YTDataSchema`() {
        youtubeRepositoryImpl.getYTDataList()
    }
}