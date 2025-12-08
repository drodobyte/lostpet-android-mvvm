package com.drodobyte.core.data.repository

import com.drodobyte.core.data.local.ImageLocalDataSource
import com.drodobyte.core.data.model.Filter
import com.drodobyte.core.data.model.Filter.All
import com.drodobyte.core.data.model.Filter.Found
import com.drodobyte.core.data.model.Filter.Lost
import com.drodobyte.core.data.model.Pet
import com.drodobyte.core.data.remote.ImageRemoteDataSource
import com.drodobyte.core.data.remote.PetRemoteDataSource
import com.drodobyte.core.data.repository.Adapter.Remote.Companion.model
import com.drodobyte.core.data.repository.Adapter.Remote.Companion.remote
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

internal class DefaultPetRepository(
    val petRemoteDataSource: PetRemoteDataSource,
    val imageLocalDataSource: ImageLocalDataSource,
    val imageRemoteDataSource: ImageRemoteDataSource,
) : PetRepository {

    override lateinit var scope: CoroutineScope

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun images() =
        imageLocalDataSource
            .get()
            .map {
                it.ifEmpty {
                    imageRemoteDataSource.petImages(10).also { new ->
                        imageLocalDataSource.set(new)
                    }
                }
            }

    override fun pets(filter: Filter) =
        when (filter) {
            All -> find { true }
            Lost -> find { it.lost }
            Found -> find { it.found }
        }

    override suspend fun save(pet: Pet) =
        petRemoteDataSource.all().find { it.id == pet.id }?.let {
            petRemoteDataSource.update(pet.remote, it.id!!).model
        } ?: petRemoteDataSource.save(pet.remote).model

    private fun find(cond: (Pet) -> Boolean) =
        flow { emit(petRemoteDataSource.all().model.filter { cond(it) }) }
}
