package com.drodobyte.core.data.remote

import okhttp3.OkHttpClient
import okhttp3.java.net.cookiejar.JavaNetCookieJar
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory.create
import java.net.CookieManager
import java.net.CookiePolicy.ACCEPT_ALL

internal object Factory {

    fun petDataSource() =
        PetRemoteDataSource(petApi())

    fun imageDataSource() =
        ImageRemoteDataSource(imageApi())

    private fun petApi() =
        api(RealPetApi::class.java, "https://rem.dbwebb.se/api/")

    private fun imageApi() =
        api(RealImageApi::class.java, "https://dog.ceo/api/")

    private fun <T> api(service: Class<T>, baseUrl: String): T =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client())
            .addConverterFactory(create())
            .build()
            .create(service)

    private fun client() =
        OkHttpClient.Builder()
            .cookieJar(JavaNetCookieJar(CookieManager().apply { setCookiePolicy(ACCEPT_ALL) }
            )).build()
}
