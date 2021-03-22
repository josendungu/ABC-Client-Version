package com.android.abc.utils


import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import com.android.abc.data.models.Client
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ClientDetailsManager(private val dataStore: DataStore<Preferences>) {

    companion object {
        val STATE_SURNAME = stringPreferencesKey("surname")
        val STATE_FIRST_NAME = stringPreferencesKey("first_name")
        val STATE_LAST_NAME = stringPreferencesKey("last_name")
        val STATE_PHONE_NUMBER = intPreferencesKey("phone_number")
        val STATE_EMAIL = stringPreferencesKey("email")
        val STATE_COUNTY = stringPreferencesKey("county")
        val STATE_TOWN = stringPreferencesKey("town")
        val STATE_ID = intPreferencesKey("id")
        val STATE_PLATE_SIZE = intPreferencesKey("number_plates_size")

    }

    suspend fun saveState(client: Client) {

        dataStore.edit {
            it[STATE_FIRST_NAME] = client.firstName
            it[STATE_LAST_NAME] = client.lastName
            it[STATE_SURNAME] = client.firstName
            it[STATE_PHONE_NUMBER] = client.phoneNumber!!
            it[STATE_EMAIL] = client.email
            it[STATE_COUNTY] = client.county
            it[STATE_TOWN] = client.town
            it[STATE_ID] = client.id!!
            it[STATE_TOWN] = client.town
            it[STATE_PLATE_SIZE] = client.plates.size

            client.plates.forEachIndexed { index, element ->
                val plateString = stringPreferencesKey("plate_${index + 1}")
                it[plateString] = element
            }


        }

    }

    val surname: Flow<String?> = dataStore.data.map {
        it[STATE_SURNAME]
    }


    val firstName: Flow<String?> = dataStore.data.map {
        it[STATE_FIRST_NAME]
    }

    val lastName: Flow<String?> = dataStore.data.map {
        it[STATE_LAST_NAME]
    }

    val email: Flow<String?> = dataStore.data.map {
        it[STATE_EMAIL]
    }

    val town: Flow<String?> = dataStore.data.map {
        it[STATE_TOWN]
    }

    val phoneNumber: Flow<Int?> = dataStore.data.map {
        it[STATE_PHONE_NUMBER]
    }

    val county: Flow<String?> = dataStore.data.map {
        it[STATE_COUNTY]
    }

    val id: Flow<Int?> = dataStore.data.map {
        it[STATE_ID]
    }


    val plates: Flow<MutableList<String>> = dataStore.data.map {
        getPlates(it)
    }


    private fun getPlates(preferences: Preferences): MutableList<String> {

        val size = preferences[STATE_PLATE_SIZE] ?: 0
        val arrayList: MutableList<String> = ArrayList()



        for (i in 0 until size) {
            val plateString = stringPreferencesKey("plate_${i + 1}")
            val plate = preferences[plateString]
            plate?.let { arrayList.add(it) }
        }


        return arrayList


    }


}