package com.drodobyte.core.data.remote

class ImageRemoteDataSource internal constructor(
    private val api: ImageUrlApi
) {
    suspend fun petImages(count: Int) = api.randomPetImageUrls(count).message
}
