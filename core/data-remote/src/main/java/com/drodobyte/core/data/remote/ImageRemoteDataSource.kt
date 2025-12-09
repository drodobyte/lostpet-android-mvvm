package com.drodobyte.core.data.remote

class ImageRemoteDataSource internal constructor(
    private val api: ImageApi
) {
    suspend fun petImages(count: Int) = api.randomBreeds(count).message
}
