package com.drodobyte.core.data.repository

import com.drodobyte.core.data.model.Pet
import com.drodobyte.core.data.remote.Api
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

internal interface Adapter {
    interface Local {
        companion object {
            val String?.model get() = this?.let { gson.fromJson(it, Type) } ?: Empty
            val List<String>.local: String get() = gson.toJson(this)

            private val gson by lazy { Gson() }
            private val Type = object : TypeToken<List<String>>() {}.type!!
            private val Empty = emptyList<String>()
        }
    }

    interface Remote {
        companion object {
            val List<Api.Pet.Response.Pet>.model get() = map { it.model }
            val Api.Pet.Response.Pet.model get() = Pet(id, name, description, image, location)
            val Pet.remote get() = Api.Pet.Response.Pet(id, name, description, image, location)
        }
    }
}
