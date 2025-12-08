package com.drodobyte.core.data.repository

import com.drodobyte.core.data.model.Pet
import com.drodobyte.core.data.remote.Response

internal interface Adapter {
    interface Local {
        companion object {
//            val Pet.local get() = map { it.toLocal }

//            private val Food.toLocal get() = LocalFood(id, name, brand, energy, protein)
        }
    }

    interface Remote {
        companion object {
            val List<Response.Pet>.model get() = map { it.model }
            val Response.Pet.model get() = Pet(id, name, description, image, location)
            //            val Response.toModel get() = foods.toModel
            val Pet.remote get() = Response.Pet(id, name, description, image, location)
//            private val List<RemFood>.toModel get() = map { it.toModel }
//            private val RemFood.toModel get() = Food(fdcId, description, brandName, energy, protein)
//            private val RemFood.protein get() = nutrientValue(Protein)
//            private val RemFood.energy get() = nutrientValue(Energy).toInt()
//            private fun RemFood.nutrientValue(what: What) =
//                foodNutrients.find { it.nutrientId == what.id }?.value ?: 0f
        }
    }
}
