package com.drodobyte.core.data.remote

import com.drodobyte.core.data.remote.Response.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

internal interface PetApi {

    suspend fun pets(): Response
    suspend fun save(pet: Pet): Pet
    suspend fun update(pet: Pet, id: Long): Pet
}

data class Response(
    val data: List<Pet>,
) {
    data class Pet(
        val id: Long? = null,
        val name: String = "",
        val description: String = "",
        val image: String = "",
        val location: String? = null,
    )
}

internal interface RealPetApi : PetApi {

    @GET("pets")
    override suspend fun pets(): Response

    @POST("pets")
    override suspend fun save(@Body pet: Pet): Pet

    @PUT("pets/{id}")
    override suspend fun update(@Body pet: Pet, @Path("id") id: Long): Pet
}
