package com.drodobyte.core.data.remote

import android.content.Context

internal object Factory {

    fun petDataSource(context: Context) =
        PetRemoteDataSource(DefaultPetApi())

    fun imageDataSource(context: Context) =
        ImageRemoteDataSource(DefaultImageApi)
}
