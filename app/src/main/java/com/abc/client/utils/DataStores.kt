package com.abc.client.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

val Context.splashDataStore: DataStore<Preferences> by preferencesDataStore(name = "splash_screen")

val Context.userDataStore: DataStore<Preferences> by preferencesDataStore(name = "user_details")