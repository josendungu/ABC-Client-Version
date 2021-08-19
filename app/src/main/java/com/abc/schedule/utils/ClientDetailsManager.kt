package com.abc.schedule.utils


import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.abc.schedule.data.models.Client
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ClientDetailsManager(private val dataStore: DataStore<Preferences>) {

    companion object {
        val STATE_SURNAME = stringPreferencesKey("surname")
        val STATE_FIRST_NAME = stringPreferencesKey("first_name")
        val STATE_LAST_NAME = stringPreferencesKey("last_name")
        val STATE_PHONE_NUMBER = stringPreferencesKey("phone_number")
        val STATE_EMAIL = stringPreferencesKey("email")
        val STATE_COUNTY = stringPreferencesKey("county")
        val STATE_TOWN = stringPreferencesKey("town")
        val STATE_ID = stringPreferencesKey("id")
        val STATE_PLATE_SIZE = intPreferencesKey("number_plates_size")

    }

    suspend fun saveState(client: Client) {

        dataStore.edit {
            it[STATE_FIRST_NAME] = client.firstName!!
            it[STATE_LAST_NAME] = client.lastName!!
            it[STATE_SURNAME] = client.surname!!
            it[STATE_PHONE_NUMBER] = client.phoneNumber!!
            it[STATE_EMAIL] = client.email!!
            it[STATE_COUNTY] = client.county!!
            it[STATE_TOWN] = client.town!!
            it[STATE_ID] = client.id!!
            it[STATE_TOWN] = client.town!!
            it[STATE_PLATE_SIZE] = client.plates?.size!!

            client.plates!!.forEachIndexed { index, element ->
                val plateString = stringPreferencesKey("plate_${index + 1}")
                it[plateString] = element
            }


        }

    }

    fun getClientData(): LiveData<Client> {

        val client = Client()

        val surname: Flow<String?> = dataStore.data.map {
            it[STATE_SURNAME]
        }

        surname.asLiveData().observeForever { value ->
            if (value != null) {
                client.surname = value
            }
        }

        val firstName: Flow<String?> = dataStore.data.map {
            it[STATE_FIRST_NAME]
        }

        firstName.asLiveData().observeForever { value ->
            if (value != null) {
                client.firstName = value
            }
        }

        val lastName: Flow<String?> = dataStore.data.map {
            it[STATE_LAST_NAME]
        }

        lastName.asLiveData().observeForever { value ->
            if (value != null) {
                client.lastName = value
            }
        }

        val email: Flow<String?> = dataStore.data.map {
            it[STATE_EMAIL]
        }

        email.asLiveData().observeForever { value ->
            if (value != null) {
                client.email = value
            }
        }

        val town: Flow<String?> = dataStore.data.map {
            it[STATE_TOWN]
        }

        town.asLiveData().observeForever { value ->
            if (value != null) {
                client.town = value
            }
        }

        val phoneNumber: Flow<String?> = dataStore.data.map {
            it[STATE_PHONE_NUMBER]
        }

        phoneNumber.asLiveData().observeForever { value ->
            if (value != null) {
                client.phoneNumber = value
            }
        }

        val county: Flow<String?> = dataStore.data.map {
            it[STATE_COUNTY]
        }

        county.asLiveData().observeForever { value ->
            if (value != null) {
                client.county = value
            }
        }

        val id: Flow<String?> = dataStore.data.map {
            it[STATE_ID]
        }

        id.asLiveData().observeForever { value ->
            if (value != null) {
                client.id = value
            }
        }


        val plates: Flow<MutableList<String>> = dataStore.data.map {
            getPlates(it)
        }

        plates.asLiveData().observeForever { value ->
            if (value != null) {
                client.plates = value
            }
        }


        return MutableLiveData(client)
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