package com.drodobyte.core.data.local

class ImageLocalDataSource internal constructor(
    private val store: Store
) {
    fun get() = store.urls()

    suspend fun set(urls: List<String>) = store.urls(urls)
}
