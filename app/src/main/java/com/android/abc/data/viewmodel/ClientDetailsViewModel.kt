package com.android.abc.data.viewmodel

import android.app.Application
import android.widget.EditText
import androidx.lifecycle.*
import com.android.abc.utils.ClientDetailsManager
import com.android.abc.data.models.Client
import com.android.abc.splashDataStore
import com.android.abc.userDataStore
import com.android.abc.utils.SplashScreenStateManager
import com.android.abc.utils.Validate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ClientDetailsViewModel(application: Application) : AndroidViewModel(application) {

    var surname: String? = null
    var firstName: String? = null
    var lastName: String? = null
    var email: String? = null
    var phoneNumber: Int? = null
    var county: String? = null
    var town: String? = null
    var id: Int? = null
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

        val client = clientDetailsManager.getClientData()
        client.value?.let { setClientData(it) }
        return client
    }




}