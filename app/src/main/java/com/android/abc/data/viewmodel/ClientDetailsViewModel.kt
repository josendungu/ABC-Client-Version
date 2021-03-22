package com.android.abc.data.viewmodel

import android.app.Application
import android.widget.EditText
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.android.abc.utils.ClientDetailsManager
import com.android.abc.data.models.Client
import com.android.abc.splashDataStore
import com.android.abc.userDataStore
import com.android.abc.utils.SplashScreenStateManager
import com.android.abc.utils.Validate

class ClientDetailsViewModel(application: Application): AndroidViewModel(application) {

    lateinit var surname: String
    lateinit var firstName: String
    lateinit var lastName: String
    lateinit var email:String
    var phoneNumber:Int? = null
    lateinit var county: String
    lateinit var town:  String
    var id: Int? = null
    lateinit var plates: MutableList<String>

    private val clientDetailsManager = application.baseContext.userDataStore.let { ClientDetailsManager(it) }


    fun setClientData(client: Client){
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

    suspend fun saveClientDetails(){
        clientDetailsManager.saveState(getClient())
    }

    fun getClient(): Client{
        return Client(surname, firstName, lastName, phoneNumber, email, county, town, id, plates)
    }


    fun getMutableClientData(): LiveData<Client> {

        clientDetailsManager.surname.asLiveData().observeForever {
            if (it != null) {
                surname = it
            }
        }

        clientDetailsManager.firstName.asLiveData().observeForever {
            if (it != null) {
                firstName = it
            }
        }

        clientDetailsManager.lastName.asLiveData().observeForever {
            if (it != null) {
                lastName = it
            }
        }

        clientDetailsManager.email.asLiveData().observeForever {
            if (it != null) {
                email = it
            }
        }

        clientDetailsManager.phoneNumber.asLiveData().observeForever {
            if (it != null) {
                phoneNumber = it
            }
        }

        clientDetailsManager.county.asLiveData().observeForever {
            if (it != null) {
                county = it
            }
        }

        clientDetailsManager.town.asLiveData().observeForever {
            if (it != null) {
                town = it
            }
        }

        clientDetailsManager.id.asLiveData().observeForever {
            if (it != null) {
                id = it
            }
        }

        clientDetailsManager.plates.asLiveData().observeForever {
            if (it != null) {
                plates = it
            }
        }

        val client = Client(surname, firstName, lastName, phoneNumber, email, county, town, id, plates)

        val clientDetails: MutableLiveData<Client> = MutableLiveData()
        clientDetails.value = client
        return clientDetails


    }

    fun validateSurname(editText: EditText): Boolean{

        val validation = Validate(surname, editText)
        return validation.stringEmpty()
                && validation.doesNotContainsSpecialCharacter()
                && validation.noWhiteSpaces()

    }

    fun validateFirstName(editText: EditText): Boolean{

        val validation = Validate(firstName, editText)
        return validation.stringEmpty() && validation.doesNotContainsSpecialCharacter() && validation.noWhiteSpaces()

    }

    fun validateLastName(editText: EditText): Boolean{

        val validation = Validate(lastName, editText)
        return validation.stringEmpty() && validation.doesNotContainsSpecialCharacter() && validation.noWhiteSpaces()

    }

    fun validateEmail(editText: EditText): Boolean{

        val validation = Validate(email, editText)

        return validation.stringEmpty() && validation.validateEmail()

    }

    fun validatePhone( editText: EditText): Boolean{

        val validation = Validate(phoneNumber.toString(), editText)
        return if (phoneNumber == null){
            editText.error = "Field can't be empty"
            false
        } else{
            validation.phoneNumber()

        }

    }

    fun validateCounty(editText: EditText): Boolean{

        val validation = Validate(county, editText)
        return validation.stringEmpty() && validation.doesNotContainsSpecialCharacter()

    }

    fun validateTown(editText: EditText): Boolean{

        val validation = Validate(town, editText)
        return validation.stringEmpty() && validation.doesNotContainsSpecialCharacter()

    }

    fun validateId(editText: EditText): Boolean{

        return if (id == null){
            editText.error = "Field can't be empty"
            false
        } else{
            true

        }



    }



}