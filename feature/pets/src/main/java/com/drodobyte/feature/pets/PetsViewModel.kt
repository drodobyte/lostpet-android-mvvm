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
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.updateAndGet
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

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
    private val images = MutableStateFlow<List<String>>(emptyList())

    val uiState = combine(
        errors,
        filter,
        pets,
        selectedPet,
        images,
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

    fun selected(image: String) =
        viewModelScope.launch {
            selectedPet.updateAndGet { it?.copy(image = image) }?.let { pet ->
                pets.updateAndGet { it.replaceById(pet.id, pet) }
            }
            images.update { emptyList() }
        }

    fun filtered(new: Filter) =
        viewModelScope.launch {
            filter.update { new }
            fetchPets(new)
                .collect {
                    pets.update { it }
                }
        }

    fun newPet() =
        viewModelScope.launch {
            val new = petRepository.save(Pet.New)
            pets.getAndUpdate { it.toMutableList().apply { add(new) } }
            selectedPet.update { new }
        }

    fun imageClicked() =
        viewModelScope.launch {
            petRepository
                .images()
                .fetch()
                .collect { fetch ->
                    images.update { fetch }
                }
        }

    data class State(
        val errors: Int? = null,
        val filter: Filter = Filter.All,
        val pets: List<Pet> = emptyList(),
        val selectedPet: Pet? = null,
        val images: List<String> = emptyList(),
        val selectedImage: String? = null,
    )

    private fun fetchPets(filter: Filter) = petRepository.pets(filter).fetch()

    private fun <T> Flow<List<T>>.fetch() =
        flowOn(Dispatchers.IO)
            .catch {
                emit(emptyList())
                errors.update { errors.value?.inc() ?: 0 }
            }

    private fun List<Pet>.replaceById(id: Long?, pet: Pet) =
        toMutableList().apply { set(indexOfFirst { id == it.id }, pet) }

    private suspend fun saveMocks() {
        runCatching {
            (1..10).onEach {
                petRepository
                    .save(
                        Pet(
                            id = it.toLong(),
                            name = "ruffo $it",
                            description = "My dog $it",
                            image = "https://images.dog.ceo/breeds/maltese/n02085936_9037.jpg",
                            location = if (Random.nextBoolean()) "Some location" else ""
                        )
                    )
            }
        }.fold({}, { errors.update { errors.value?.inc() ?: 0 } })

        fetchPets(Filter.All)
            .collect { fetch ->
                pets.update { fetch }
            }
    }
}
