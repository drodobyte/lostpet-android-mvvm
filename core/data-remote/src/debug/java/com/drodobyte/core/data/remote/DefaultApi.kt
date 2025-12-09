package com.drodobyte.core.data.remote

import com.drodobyte.core.data.remote.Api.Image
import com.drodobyte.core.data.remote.Api.Pet.Response
import com.drodobyte.core.data.remote.Api.Pet.Response.Pet
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

internal object DefaultImageApi : Image {

    override suspend fun randomBreeds(count: Int) =
        Image.Response(
            listOf(
                "https://images.dog.ceo/breeds/otterhound/n02091635_699.jpg",
                "https://images.dog.ceo/breeds/terrier-patterdale/320px-05078045_Patterdale_Terrier.jpg",
                "https://images.dog.ceo/breeds/terrier-bedlington/n02093647_3464.jpg",
                "https://images.dog.ceo/breeds/australian-kelpie/Resized_20200214_191118_346649120350209.jpg",
                "https://images.dog.ceo/breeds/gaddi-indian/Gaddi.jpg",
                "https://images.dog.ceo/breeds/stbernard/n02109525_11285.jpg",
                "https://images.dog.ceo/breeds/chow/n02112137_13649.jpg",
                "https://images.dog.ceo/breeds/danish-swedish-farmdog/ebba_001.jpg",
                "https://images.dog.ceo/breeds/terrier-wheaten/n02098105_91.jpg",
                "https://images.dog.ceo/breeds/bulldog-french/n02108915_4901.jpg"
            )
        )
}

internal object DefaultPetApi : Api.Pet {

    private val mutex = Mutex()
    private val pets = mutableListOf<Pet>()
    private var id = 0L

    override suspend fun pets() =
        mutex.withLock { Response(pets) }

    override suspend fun save(pet: Pet) =
        mutex.withLock {
            pet.copy(id = id++).also {
                pets.add(it)
            }
        }

    override suspend fun update(pet: Pet, id: Long) =
        mutex.withLock {
            pets.replaceAll {
                if (id == it.id) pet else it
            }
            pet
        }
}
