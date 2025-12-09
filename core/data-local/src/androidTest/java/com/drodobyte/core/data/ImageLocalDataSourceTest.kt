package com.drodobyte.core.data

import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.drodobyte.core.data.local.ImageLocalDataSource
import com.drodobyte.core.data.local.Store
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule
import org.junit.rules.TemporaryFolder

@RunWith(AndroidJUnit4::class)
class ImageLocalDataSourceTest {
    @get:Rule
    val tmpFolder: TemporaryFolder = TemporaryFolder.builder().assureDeletion().build()

    @Test
    fun nullReturnedAtFirst() = runTest {
        dataSource()
            .get()
            .first()
            .let {
                assertNull(it)
            }
    }

    @Test
    fun returnsRightValues() = runTest {
        dataSource().apply { set("some urls") }
            .get()
            .first()
            .let {
                assertEquals(it, "some urls")
            }
    }

    private fun dataSource() =
        ImageLocalDataSource(
            Store(
                PreferenceDataStoreFactory.create(
                    produceFile = { tmpFolder.newFile("store.preferences_pb") }
                )))
}
