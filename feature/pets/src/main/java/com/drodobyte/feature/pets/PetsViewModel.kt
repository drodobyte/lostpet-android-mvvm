package com.drodobyte.feature.pets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.drodobyte.core.data.model.Filter
import com.drodobyte.core.data.model.Pet
import com.drodobyte.core.data.repository.PetRepository
import com.drodobyte.domain.usecase.RecommendedIntakeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
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
    private val selected = MutableStateFlow<Pet?>(null)
    private val filter = MutableStateFlow(Filter.All)
    private val pets = MutableStateFlow<List<Pet>>(emptyList())
    private val images = MutableStateFlow<List<String>>(emptyList())

    val uiState = combine(
        errors,
        selected,
        filter,
        images(),
        pets,
    ) { errors, selected, filter, images, pets ->
        combineState(errors, selected, filter, images, pets)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = State()
    )

    fun selected(pet: Pet?) = selected.set { pet }

    fun filtered(new: Filter) =
        viewModelScope.launch {
            fetchPets(new)
        }.also { filter.set { new } }

    fun newPet() {
        viewModelScope.launch {
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

            fetchPets(Filter.All)
        }
    }

    private suspend fun fetchPets(filter: Filter) {
        petRepository
            .pets(filter)
            .flowOn(Dispatchers.IO)
            .collect {
                println("caniep petsss")
                pets.set { it }
            }
    }

    data class State(
        val errors: Int? = null,
        val selected: Pet? = null,
        val filter: Filter = Filter.All,
        val images: List<String> = emptyList(),
        val pets: List<Pet> = emptyList(),
    )

    private fun combineState(
        errors: Int?,
        selected: Pet?,
        filter: Filter,
        images: List<String>,
        pets: List<Pet>,
    ) =
        State(errors, selected, filter, images, pets)

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    private fun pets(filter: Filter) =
        petRepository
            .pets(filter)
            .catch {
                emit(emptyList())
                errors.set { errors.value?.inc() ?: 0 }
            }

    private fun images() =
        petRepository
            .images()
            .catch {
                emit(emptyList())
                errors.set { errors.value?.inc() ?: 0 }
            }

    private fun <T> MutableStateFlow<T>.set(data: () -> T) =
        viewModelScope.launch { update { data() } }
}
