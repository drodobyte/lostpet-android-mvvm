package com.drodobyte.core.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.map

internal class Store(
    private val dataStore: DataStore<Preferences>
) {

    fun urls() = dataStore.data.map { it[urls] }

    suspend fun urls(str: String) {
        dataStore.updateData { pref ->
            pref.toMutablePreferences().also {
                it[urls] = str
            }
        }
    }

    private companion object {
        val urls = stringPreferencesKey("urls")
    }
}
