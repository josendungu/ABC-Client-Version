package com.abc.schedule.data.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.abc.schedule.data.models.Client
import com.abc.schedule.utils.ClientDetailsManager
import com.abc.schedule.utils.userDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ClientDetailsViewModel(application: Application) : AndroidViewModel(application) {

    var surname: String? = null
    var firstName: String? = null
    var lastName: String? = null
    var email: String? = null
    var phoneNumber: String? = null
    var county: String? = null
    var town: String? = null
    var id: String? = null
    var plates: MutableList<String>? = null

    private val clientDetailsManager =
        application.baseContext.userDataStore.let { ClientDetailsManager(it) }


    fun setClientData(client: Client) {
        surname = client.surname
        firstName = client.firstName
        lastName = client.lastName
        email = client.email
        phoneNumber = client.phoneNumber
        county = client.county
        town = client.town
        id = client.id
        plates = client.plates
    }

    fun saveClientDetails() {
        viewModelScope.launch(Dispatchers.IO) {
            clientDetailsManager.saveState(getClient())
        }
    }

    fun getClient(): Client {
        return Client(surname, firstName, lastName, phoneNumber, email, county, town, id, plates)
    }


    fun fetchClientData(): LiveData<Client> {

        return clientDetailsManager.getClientData()
    }




}