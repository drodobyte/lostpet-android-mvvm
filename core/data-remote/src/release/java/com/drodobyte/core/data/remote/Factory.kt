package com.drodobyte.core.data.remote

import android.content.Context
import net.gotev.cookiestore.SharedPreferencesCookieStore
import net.gotev.cookiestore.okhttp.JavaNetCookieJar
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory.create
import java.net.CookieManager
import java.net.CookiePolicy.ACCEPT_ALL

internal object Factory {

    fun petDataSource(context: Context) =
        PetRemoteDataSource(petApi(context))

    fun imageDataSource(context: Context) =
        ImageRemoteDataSource(imageApi(context))

    private fun petApi(context: Context) =
        api(context, DefaultPetApi::class.java, "https://rem.dbwebb.se/api/")

    private fun imageApi(context: Context) =
        api(context, DefaultImageApi::class.java, "https://dog.ceo/api/")

    private fun <T> api(context: Context, service: Class<T>, baseUrl: String): T =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client(context))
            .addConverterFactory(create())
            .build()
            .create(service)

    private fun client(context: Context) =
        OkHttpClient.Builder()
            .cookieJar(
                JavaNetCookieJar(
                    CookieManager(
                        SharedPreferencesCookieStore(context, "cookies"), ACCEPT_ALL
                    )
                )
            ).build()
}
