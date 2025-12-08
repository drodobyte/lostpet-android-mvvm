package com.drodobyte.core.data.repository

import com.drodobyte.core.data.model.Filter
import com.drodobyte.core.data.model.Pet
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

interface PetRepository {
    var scope: CoroutineScope
    fun images(): Flow<List<String>>
    fun pets(filter: Filter): Flow<List<Pet>>
    suspend fun save(pet: Pet): Pet
}
