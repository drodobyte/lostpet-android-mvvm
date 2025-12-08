package com.drodobyte.feature.pets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.drodobyte.core.data.model.Filter
import com.drodobyte.core.data.model.Pet
import com.drodobyte.core.data.repository.PetRepository
import com.drodobyte.domain.usecase.RecommendedIntakeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PetsViewModel @Inject constructor(
    private val petRepository: PetRepository,
    private val useCase: RecommendedIntakeUseCase,
) : ViewModel() {
    init {
        petRepository.scope = viewModelScope
    }

    private val errors = MutableStateFlow<Int?>(null)
    private val filter = MutableStateFlow(Filter.All)
    private val pets = MutableStateFlow<List<Pet>>(emptyList())
    private val selectedPet = MutableStateFlow<Pet?>(null)

    val uiState = combine(
        errors,
        filter,
        pets,
        selectedPet,
        fetchImages(),
    ) { errors, filter, pets, selectedPet, images ->
        State(errors, filter, pets, selectedPet, images)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = State()
    )

    fun selected(pet: Pet?) =
        viewModelScope.launch {
            selectedPet.update { pet }
        }

    fun edited(pet: Pet) =
        viewModelScope.launch {
            val saved = petRepository.save(pet)
            selectedPet.update { saved }
            pets.update { it.addOrReplace(saved) }
        }

    fun filtered(new: Filter) =
        viewModelScope.launch {
            filter.update { new }
            fetchPets(new)
                .collect {
                    pets.update { it }
                }
        }

    fun newPet() = edited(Pet.New)

    data class State(
        val errors: Int? = null,
        val filter: Filter = Filter.All,
        val pets: List<Pet> = emptyList(),
        val selectedPet: Pet? = null,
        val images: List<String> = emptyList(),
        val selectedImage: String? = null,
    )

    private fun fetchImages() = petRepository.images().fetch()

    private fun fetchPets(filter: Filter) = petRepository.pets(filter).fetch()

    private fun <T> Flow<List<T>>.fetch() =
        flowOn(Dispatchers.IO)
            .catch {
                emit(emptyList())
                errors.update { errors.value?.inc() ?: 0 }
            }


    private fun List<Pet>.addOrReplace(pet: Pet) =
        toMutableList().apply {
            indexOfFirst { pet.id == it.id }.takeIf { it >= 0 }?.let { set(it, pet) } ?: add(pet)
        }
}
