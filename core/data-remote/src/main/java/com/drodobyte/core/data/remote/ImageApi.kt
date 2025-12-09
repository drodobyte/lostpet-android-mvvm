package com.drodobyte.core.data.remote

import retrofit2.http.GET
import retrofit2.http.Path

internal interface ImageApi {

    suspend fun randomBreeds(count: Int): ImageResponse
}

data class ImageResponse(val message: List<String>)

internal interface RealImageApi : ImageApi {

    @GET("breeds/image/random/{count}")
    override suspend fun randomBreeds(@Path("count") count: Int): ImageResponse
}
