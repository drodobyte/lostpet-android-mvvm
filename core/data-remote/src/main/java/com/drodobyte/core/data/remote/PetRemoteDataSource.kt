package com.drodobyte.core.data.remote

import com.drodobyte.core.data.remote.Api.Pet.Response.Pet

class PetRemoteDataSource internal constructor(
    private val api: Api.Pet
) {
    suspend fun all() = api.pets().data
    suspend fun insert(pet: Pet) = api.save(pet)
    suspend fun update(pet: Pet, id: Long) = api.update(pet, id)
}
