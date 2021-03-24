package com.android.abc.data.viewmodel

import android.app.Application
import android.widget.EditText
import androidx.lifecycle.AndroidViewModel
import com.android.abc.R
import com.android.abc.utils.Validate

class SharedViewModel(application: Application): AndroidViewModel(application){

    fun validateSurname(surname: String, editText: EditText): Boolean {

        val validation = Validate(surname, editText)
        return validation.stringEmpty()
                && validation.doesNotContainsSpecialCharacter()
                && validation.noWhiteSpaces()

    }

    fun validateFirstName(firstName: String, editText: EditText): Boolean {

        val validation = Validate(firstName, editText)
        return validation.stringEmpty() && validation.doesNotContainsSpecialCharacter() && validation.noWhiteSpaces()

    }

    fun validateLastName(lastName: String, editText: EditText): Boolean {

        val validation = Validate(lastName, editText)
        return validation.stringEmpty() && validation.doesNotContainsSpecialCharacter() && validation.noWhiteSpaces()

    }

    fun validateEmail(email:String, editText: EditText): Boolean {

        val validation = Validate(email, editText)

        return validation.stringEmpty() && validation.validateEmail()

    }

    fun validatePhone(phoneNumber: String, editText: EditText): Boolean {

        val validation = Validate(phoneNumber, editText)
        return if (phoneNumber.isEmpty()) {
            editText.error = "Field can't be empty"
            false
        } else {
            validation.phoneNumber()

        }

    }

    fun validateCounty(county: String, editText: EditText): Boolean {

        val validation = Validate(county, editText)
        return validation.stringEmpty() && validation.doesNotContainsSpecialCharacter()

    }

    fun validateTown(town: String, editText: EditText): Boolean {

        val validation = Validate(town, editText)
        return validation.stringEmpty() && validation.doesNotContainsSpecialCharacter()

    }

    fun validateId(id: String, editText: EditText): Boolean {

        return if (id.isEmpty()) {
            editText.error = "Field can't be empty"
            false
        } else {
            true

        }


    }



    fun convertIntToString(string: String): Int?{
        return if (string == ""){
            null
        } else {
            Integer.parseInt(string)
        }
    }


}