package com.drodobyte.core.data.remote

class ImageRemoteDataSource internal constructor(
    private val api: Api.Image
) {
    suspend fun petImages(count: Int) = api.randomBreeds(count).message
}
