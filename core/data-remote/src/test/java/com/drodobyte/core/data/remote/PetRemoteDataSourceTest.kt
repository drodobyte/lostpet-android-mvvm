package com.drodobyte.core.data.remote

import com.drodobyte.core.data.remote.Api.Pet.Response.Pet
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class PetRemoteDataSourceTest {

    @Test
    fun `return 0 pets at first`() = runTest {
        assertEquals(0, dataSource().all().size)
    }

    @Test
    fun `add returns newly created pet`() = runTest {
        dataSource().apply {
            runBlocking {
                assertEquals(Pet(0, "a"), insert(Pet(name = "a")))
            }
        }
    }

    @Test
    fun `adds pets correctly`() = runTest {
        dataSource().apply {
            runBlocking {
                insert(Pet(name = "a"))
                insert(Pet(name = "b"))
            }
            assertEquals(listOf(Pet(0, "a"), Pet(1, "b")), all())
        }
    }

    @Test
    fun `updates pets correctly`() = runTest {
        dataSource().apply {
            runBlocking {
                insert(Pet(name = "a"))
                insert(Pet(name = "b"))
                update(Pet(name = "c"), 1)
            }
            assertEquals(listOf(Pet(0, "a"), Pet(1, "c")), all())
        }
    }

    @Test
    fun `update returns updated pet`() = runTest {
        dataSource().apply {
            runBlocking {
                insert(Pet(name = "a"))
            }
            assertEquals(Pet(0, "c"), update(Pet(name = "c"), 0))
        }
    }

    private fun dataSource() = PetRemoteDataSource(DefaultPetApi())
}
