package com.drodobyte.core.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.map

internal class Store(
    private val context: Context
) {
    private val gson by lazy { Gson() }
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "store")

    fun urls() = context.dataStore.data.map { it[urls].strings }

    suspend fun urls(strings: List<String>) {
        context.dataStore.updateData { pref ->
            pref.toMutablePreferences().also {
                it[urls] = strings.json
            }
        }
    }

    private val String?.strings get() = this?.let { gson.fromJson(it, Type) } ?: Empty
    private val List<String>.json get() = gson.toJson(this)

    private companion object {
        val Type = object : TypeToken<List<String>>() {}.type!!
        val Empty = emptyList<String>()
        val urls = stringPreferencesKey("urls")
    }
}
