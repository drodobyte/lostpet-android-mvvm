package com.drodobyte.feature.pets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.drodobyte.core.data.model.Filter
import com.drodobyte.core.data.model.Pet
import com.drodobyte.core.data.repository.PetRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PetsViewModel @Inject constructor(
    private val petRepository: PetRepository,
) : ViewModel() {

    private val error = MutableStateFlow(false)
    private val pets = MutableStateFlow<List<Pet>>(emptyList())
    private val selectedPet = MutableStateFlow<Pet?>(null)

    val uiState = combine(
        error,
        pets,
        selectedPet,
        petRepository.images().catch { error.update { true } },
    ) { error, pets, selectedPet, images ->
        State(error, pets, selectedPet, images)
    }.onEach {
        if (it.error) error.update { false } // one-shot state clear
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = State()
    )

    fun selected(pet: Pet?) =
        viewModelScope.launch {
            selectedPet.update { pet }
        }

    fun newPet() =
        edited(Pet.New)

    fun edited(pet: Pet) =
        viewModelScope.launch {
            runCatching {
                val saved = petRepository.persist(pet)
                selectedPet.update { saved }
                pets.update { it.addOrReplace(saved) }
            }.onFailure {
                error.update { true }
            }
        }

    fun filtered(new: Filter) =
        viewModelScope.launch {
            petRepository
                .pets(new)
                .catch { error.update { true } }
                .collect { fetch ->
                    pets.update { fetch }
                }
        }

    data class State(
        val error: Boolean = false,
        val pets: List<Pet> = emptyList(),
        val selectedPet: Pet? = null,
        val images: List<String> = emptyList(),
    )

    private fun List<Pet>.addOrReplace(pet: Pet) =
        toMutableList().apply {
            indexOfFirst { pet.id == it.id }.takeIf { it >= 0 }?.let { set(it, pet) } ?: add(pet)
        }
}
