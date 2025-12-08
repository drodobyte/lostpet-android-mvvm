package com.drodobyte.core.data.repository

import com.drodobyte.core.data.model.Filter
import com.drodobyte.core.data.model.Pet
import kotlinx.coroutines.flow.Flow

interface PetRepository {
    fun images(): Flow<List<String>>
    fun pets(filter: Filter): Flow<List<Pet>>
    suspend fun persist(pet: Pet): Pet
}
