package com.drodobyte.core.data.remote

import com.drodobyte.core.data.remote.Response.Pet

class PetRemoteDataSource internal constructor(
    private val api: PetApi
) {
    suspend fun all() = api.pets().data
    suspend fun get(id: String) = api.pet(id)
    suspend fun save(pet: Pet) = api.save(pet)
    suspend fun update(pet: Pet, id: String) = api.update(pet, id)
}
