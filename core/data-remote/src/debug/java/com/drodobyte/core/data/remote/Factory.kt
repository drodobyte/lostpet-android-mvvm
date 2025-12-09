package com.drodobyte.core.data.remote

internal object Factory {

    fun petDataSource() =
        PetRemoteDataSource(DefaultPetApi)

    fun imageDataSource() =
        ImageRemoteDataSource(DefaultImageApi)
}
