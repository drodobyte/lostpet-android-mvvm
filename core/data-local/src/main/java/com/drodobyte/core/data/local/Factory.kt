package com.drodobyte.core.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

internal object Factory {

    fun dataSource(context: Context) =
        ImageLocalDataSource(Store(context.dataStore))

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "store")
}
