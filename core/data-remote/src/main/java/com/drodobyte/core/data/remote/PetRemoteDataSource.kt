package com.drodobyte.core.data.remote

import com.drodobyte.core.data.remote.Response.Pet

class PetRemoteDataSource internal constructor(
    private val api: PetApi
) {
    suspend fun all() = api.pets().data
    suspend fun get(id: Long) = api.pet(id)
    suspend fun insert(pet: Pet) = api.save(pet)
    suspend fun update(pet: Pet, id: Long) = api.update(pet, id)
}
