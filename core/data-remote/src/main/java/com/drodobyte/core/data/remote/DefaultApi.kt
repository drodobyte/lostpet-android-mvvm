package com.drodobyte.core.data.remote

import com.drodobyte.core.data.remote.Api.Image
import com.drodobyte.core.data.remote.Api.Pet.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

internal interface DefaultImageApi : Api.Image {

    @GET("breeds/image/random/{count}")
    override suspend fun randomBreeds(@Path("count") count: Int): Image.Response
}

internal interface DefaultPetApi : Api.Pet {

    @GET("pets")
    override suspend fun pets(): Response

    @POST("pets")
    override suspend fun save(@Body pet: Response.Pet): Response.Pet

    @PUT("pets/{id}")
    override suspend fun update(@Body pet: Response.Pet, @Path("id") id: Long): Response.Pet
}
