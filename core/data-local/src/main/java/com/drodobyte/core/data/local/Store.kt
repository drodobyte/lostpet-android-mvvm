package com.drodobyte.core.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

internal class Store(
    private val context: Context
) {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "store")

    fun urls() = context.dataStore.data.map { it[urls] }

    suspend fun urls(str: String) {
        context.dataStore.updateData { pref ->
            pref.toMutablePreferences().also {
                it[urls] = str
            }
        }
    }

    private companion object {
        val urls = stringPreferencesKey("urls")
    }
}
