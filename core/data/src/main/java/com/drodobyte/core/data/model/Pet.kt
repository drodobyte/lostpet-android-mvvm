package com.drodobyte.core.data.model

//@Serializable
data class Pet(
    val id: Long? = null,
    val name: String = "",
    val description: String = "",
    val image: String = "",
    val location: String? = null,
) {
    val lost = location.isNullOrBlank()
    val found = !lost

    companion object {
        val None = Pet()
    }
}

