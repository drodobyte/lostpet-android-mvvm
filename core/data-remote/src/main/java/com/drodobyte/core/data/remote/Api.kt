package com.drodobyte.core.data.remote

interface Api {

    interface Image {
        suspend fun randomBreeds(count: Int): Response

        data class Response(val message: List<String>)
    }

    interface Pet {
        suspend fun pets(): Response
        suspend fun save(pet: Response.Pet): Response.Pet
        suspend fun update(pet: Response.Pet, id: Long): Response.Pet

        data class Response(
            val data: List<Pet>,
        ) {
            data class Pet(
                val id: Long? = null,
                val name: String = "",
                val description: String = "",
                val image: String = "",
                val location: String? = null,
            )
        }
    }
}
