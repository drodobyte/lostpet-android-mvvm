package com.drodobyte.core.data.repository

import com.drodobyte.core.data.model.Pet
import com.drodobyte.core.data.remote.Response

internal interface Adapter {
    interface Remote {
        companion object {
            val List<Response.Pet>.model get() = map { it.model }
            val Response.Pet.model get() = Pet(id, name, description, image, location)
            val Pet.remote get() = Response.Pet(id, name, description, image, location)
        }
    }
}
