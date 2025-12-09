package com.drodobyte.data.remote

import com.drodobyte.core.data.remote.DefaultImageApi
import com.drodobyte.core.data.remote.ImageRemoteDataSource
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Test

import org.junit.Assert.*
import java.lang.IllegalArgumentException

class ImageRemoteDataSourceTest {

    @Test
    fun `on count=-1, throws exception`() = runTest {
        assertThrows(IllegalArgumentException::class.java) {
            runBlocking {
                dataSource().petImages(-1)
            }
        }
    }

    @Test
    fun `on count=0, return 0 images`() = runTest {
        assertEquals(0, dataSource().petImages(0).size)
    }

    @Test
    fun `on count=10, return 10 images`() = runTest {
        assertEquals(10, dataSource().petImages(10).size)
    }

    @Test
    fun `on count=15, return 15 images`() = runTest {
        assertEquals(15, dataSource().petImages(15).size)
    }

    private fun dataSource() = ImageRemoteDataSource(DefaultImageApi)
}
