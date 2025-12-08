package com.drodobyte.core.data.repository

import com.drodobyte.core.data.local.ImageLocalDataSource
import com.drodobyte.core.data.model.Filter
import com.drodobyte.core.data.model.Filter.All
import com.drodobyte.core.data.model.Filter.Found
import com.drodobyte.core.data.model.Filter.Lost
import com.drodobyte.core.data.model.Pet
import com.drodobyte.core.data.remote.ImageRemoteDataSource
import com.drodobyte.core.data.remote.PetRemoteDataSource
import com.drodobyte.core.data.remote.Response
import com.drodobyte.core.data.repository.Adapter.Remote.Companion.model
import com.drodobyte.core.data.repository.Adapter.Remote.Companion.remote
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

internal class DefaultPetRepository(
    val petRemoteDataSource: PetRemoteDataSource,
    val imageLocalDataSource: ImageLocalDataSource,
    val imageRemoteDataSource: ImageRemoteDataSource,
    val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : PetRepository {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun images() =
        imageLocalDataSource
            .get()
            .flowOn(dispatcher)
            .map {
                it.ifEmpty {
                    imageRemoteDataSource.petImages(10).also { new ->
                        imageLocalDataSource.set(new)
                    }
                }
            }

    override fun pets(filter: Filter) =
        flow {
            emit(
                when (filter) {
                All -> find { true }
                Lost -> find { it.lost }
                Found -> find { it.found }
            }
            )
        }.flowOn(dispatcher)

    override suspend fun persist(pet: Pet) =
        withContext(dispatcher) {
            upsert(pet.remote).model
        }

    private suspend fun upsert(pet: Response.Pet) =
        petRemoteDataSource.all().find { it.id == pet.id }?.let {
            petRemoteDataSource.update(pet, it.id!!)
        } ?: petRemoteDataSource.insert(pet)

    private suspend fun find(cond: (Pet) -> Boolean) =
        petRemoteDataSource.all().model.filter { cond(it) }
}
