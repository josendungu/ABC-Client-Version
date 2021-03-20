package com.android.abc

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SplashScreenStateManager(private val dataStore: DataStore<Preferences>) {


    companion object {
        val STATE_KEY = booleanPreferencesKey("state")
    }


    suspend fun saveState(boolean: Boolean){

        dataStore.edit {
            it[STATE_KEY] = boolean
        }

    }

    val state: Flow<Boolean> = dataStore.data.map {
        it[STATE_KEY] ?: false
    }


}