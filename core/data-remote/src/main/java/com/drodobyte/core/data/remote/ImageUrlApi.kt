package com.drodobyte.core.data.remote

import retrofit2.http.GET
import retrofit2.http.Path

internal interface ImageUrlApi {

    suspend fun randomPetImageUrls(count: Int): ImageUrlResponseResponse
}

data class ImageUrlResponseResponse(val message: List<String>, val status: String)

internal interface RealImageUrlApi : ImageUrlApi {

    @GET("breeds/image/random/{count}")
    override suspend fun randomPetImageUrls(@Path("count") count: Int): ImageUrlResponseResponse
}
