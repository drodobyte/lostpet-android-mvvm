package com.drodobyte.feature.pets

import com.drodobyte.core.data.model.Filter
import com.drodobyte.core.data.model.Filter.All
import com.drodobyte.core.data.model.Pet
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import com.drodobyte.feature.pets.PetsViewModel.State
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.test.TestScope
import org.mockito.kotlin.doAnswer

class PetsViewModelTest {

    @Test
    fun `at first emit empty state`() = test { vm ->
        assertEquals(State(), vm.uiState.first())
    }

    @Test
    fun `on selected pet, emit state with selectedPet`() = test {
        it.selected(Pet(name = "a"))

        assertEquals(
            State(selectedPet = Pet(name = "a")), it.state()
        )
    }

    @Test
    fun `on new pet, emit state with newly pet in pets and selectedPet`() = test {
        it.newPet()

        assertEquals(
            State(pets = listOf(Pet.New), selectedPet = Pet.New), it.state()
        )
    }

    @Test
    fun `on edited pet, emit state with edited pet in pets and selectedPet`() = test { vm ->
        vm.edited(Pet(name = "a"))

        assertEquals(
            State(pets = listOf(Pet(name = "a")), selectedPet = Pet(name = "a")), vm.state()
        )
    }

    @Test
    fun `on images, emit state with images`() = test(
        images = listOf("image1", "image2")
    ) { vm ->

        assertEquals(
            State(images = listOf("image1", "image2")), vm.state()
        )
    }

    @Test
    fun `on filtered pets, emit state with pets`() = test(
        pets = listOf(Pet(name = "a"), Pet(name = "b"))
    ) {
        it.filtered(All)

        assertEquals(
            State(pets = listOf(Pet(name = "a"), Pet(name = "b"))), it.state()
        )
    }

    private fun test(
        images: List<String> = emptyList(),
        pets: List<Pet> = emptyList(),
        tested: suspend TestScope.(PetsViewModel) -> Unit
    ) = runTest {
        tested(PetsViewModel(mock {
            on { images() } doReturn flow { emit(images) }
            on { pets(any<Filter>()) } doReturn flow { emit(pets) }
            onBlocking { persist(any<Pet>()) } doAnswer { it.arguments[0] as Pet }
        }))
    }

    private suspend fun PetsViewModel.state() = uiState.drop(1).first()
}
