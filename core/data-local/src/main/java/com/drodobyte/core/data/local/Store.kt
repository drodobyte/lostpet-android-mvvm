package com.drodobyte.core.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class Store(
    private val context: Context
) {
    private val gson by lazy { Gson() }
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "store")

    fun urls(): Flow<List<String>> = context.dataStore.data.map { pref ->
        pref[urls]?.let { gson.fromJson(it, Type) } ?: Empty
    }

    suspend fun urls(new: List<String>) {
        context.dataStore.updateData { pref ->
            pref.toMutablePreferences().also {
                it[urls] = gson.toJson(new)
            }
        }
    }

    private companion object {
        val Type = object : TypeToken<List<String>>() {}.type!!
        val Empty = emptyList<String>()
        val urls = stringPreferencesKey("urls")
    }
}
