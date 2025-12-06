package com.drodobyte.core.data.repository

import com.drodobyte.core.data.model.Pet
import com.drodobyte.core.data.remote.ImageRemoteDataSource
import com.drodobyte.core.data.remote.PetRemoteDataSource
import com.drodobyte.core.data.repository.Adapter.Remote.Companion.model
import com.drodobyte.core.data.repository.Adapter.Remote.Companion.remote
import com.drodobyte.core.data.model.Filter
import com.drodobyte.core.data.model.Filter.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

internal class DefaultPetRepository(
    val petRemoteDataSource: PetRemoteDataSource,
    val imageRemoteDataSource: ImageRemoteDataSource,
) : PetRepository {

    override lateinit var scope: CoroutineScope

    private val mutex = Mutex()
    private var imageCache: List<String> = emptyList()

    override fun images() = flow {
        emit(
            if (mutex.withLock { imageCache.isEmpty() }) {
                imageRemoteDataSource.petImages(10).also {
                    mutex.withLock { imageCache = it }
                }
            } else {
                mutex.withLock { imageCache }
            }
        )
    }

    override fun pets(filter: Filter) =
        when (filter) {
            All -> find { true }
            Lost -> find { it.lost }
            Found -> find { it.found }
        }

    override suspend fun save(pet: Pet) {
        petRemoteDataSource.save(pet.remote)
    }

    private fun find(cond: (Pet) -> Boolean) =
        flow { emit(petRemoteDataSource.all().model.filter { cond(it) }) }
}
