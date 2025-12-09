package com.drodobyte.core.data.repository

import com.drodobyte.core.data.local.ImageLocalDataSource
import com.drodobyte.core.data.model.Filter.All
import com.drodobyte.core.data.model.Filter.Found
import com.drodobyte.core.data.model.Filter.Lost
import com.drodobyte.core.data.model.Pet
import com.drodobyte.core.data.remote.ImageRemoteDataSource
import com.drodobyte.core.data.remote.PetRemoteDataSource
import com.drodobyte.core.data.repository.Adapter.Local.Companion.local
import com.drodobyte.core.data.repository.Adapter.Remote.Companion.remote
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.Assert.assertEquals
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.inOrder
import org.mockito.kotlin.mock
import org.mockito.kotlin.never
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoInteractions

class DefaultPetRepositoryTest {

    @Test
    fun `new pets are inserted`() = test(
        pets = emptyList()
    ) { petRemote, _, _ ->

        persist(Pet(name = "a"))
        persist(Pet(name = "b"))

        inOrder(petRemote) {
            verify(petRemote).insert(Pet(name = "a").remote)
            verify(petRemote).insert(Pet(name = "b").remote)
        }
        verify(petRemote, never()).update(any(), any())
    }

    @Test
    fun `old pets are updated`() = test(
        pets = listOf(Pet(1, name = "a"), Pet(2, name = "b", location = "place"))
    ) { petRemote, _, _ ->

        persist(Pet(1, name = "c"))
        persist(Pet(2, name = "d"))

        inOrder(petRemote) {
            verify(petRemote).update(Pet(1, name = "c").remote, 1)
            verify(petRemote).update(Pet(2, name = "d").remote, 2)
        }
    }

    @Test
    fun `get all pets`() = test(
        pets = listOf(Pet(name = "a"), Pet(name = "b", location = "place"))
    ) { petRemote, _, _ ->

        val pets = pets(All).first()

        assertEquals(listOf(Pet(name = "a"), Pet(name = "b", location = "place")), pets)
        verify(petRemote).all()
    }

    @Test
    fun `get found pets`() = test(
        pets = listOf(Pet(name = "a"), Pet(name = "b", location = "place"))
    ) { petRemote, _, _ ->

        val pets = pets(Found).first()

        assertEquals(listOf(Pet(name = "b", location = "place")), pets)
        verify(petRemote).all()
    }

    @Test
    fun `get lost pets`() = test(
        pets = listOf(Pet(name = "a"), Pet(name = "b", location = "place"))
    ) { petRemote, _, _ ->

        val pets = pets(Lost).first()

        assertEquals(listOf(Pet(name = "a")), pets)
        verify(petRemote).all()
    }

    @Test
    fun `uncached images are fetched and cached`() = test(
        localImages = listOf(),
        remoteImages = listOf("remote images")
    ) { _, imagesLocal, imagesRemote ->

        images().first()

        inOrder(imagesLocal, imagesRemote) {
            verify(imagesLocal).get()
            verify(imagesRemote).petImages(any())
            verify(imagesLocal).set(listOf("remote images").local)
        }
    }

    @Test
    fun `cached images are returned`() = test(
        localImages = listOf("local images"),
        remoteImages = listOf("remote images")
    ) { _, imagesLocal, imagesRemote ->

        images().first()

        verify(imagesLocal).get()
        verifyNoInteractions(imagesRemote)
        verify(imagesLocal, never()).set(any())
    }

    private fun test(
        pets: List<Pet> = emptyList(),
        localImages: List<String> = emptyList(),
        remoteImages: List<String> = emptyList(),
        tested: suspend PetRepository.(PetRemoteDataSource, ImageLocalDataSource, ImageRemoteDataSource) -> Unit
    ) = runTest {
        val pet = mock<PetRemoteDataSource> {
            on { runBlocking { all() } } doReturn pets.remote
            on { runBlocking { insert(any()) } } doReturn Pet().remote
            on { runBlocking { update(any(), any()) } } doReturn Pet().remote
        }
        val imageLocal = mock<ImageLocalDataSource> {
            on { get() } doReturn flow { emit(localImages.local) }
            on { runBlocking { set(any()) } } doReturn Unit
        }
        val imageRemote = mock<ImageRemoteDataSource> {
            on { runBlocking { petImages(any()) } } doReturn remoteImages
        }
        runBlocking {
            tested(
                DefaultPetRepository(
                    pet,
                    imageLocal,
                    imageRemote
                ), pet, imageLocal, imageRemote
            )
        }
    }
}
