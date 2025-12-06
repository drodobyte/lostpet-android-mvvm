package com.drodobyte.core.data.local

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ImageUrlDataSource internal constructor(
    private val dao: Any,
) {
    suspend fun get(): Flow<List<String>> = flow { emit(emptyList()) }

    suspend fun set(urls: List<String>) {}
}
