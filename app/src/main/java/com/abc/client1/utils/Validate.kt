package com.abc.client1.utils

import android.util.Log
import android.widget.EditText


class Validate(private val value: String, private val editText: EditText) {

    private val tag = "Validate"

    private val containWhiteSpace = Regex("\\A\\w{3,20}\\z")
    private val emailPattern = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})".toRegex()
    private val phoneNumberPattern = Regex("[0-9]{10}")
    private val atLeastOneSpecialCharacter = Regex("(?=.*[@#$%^&+=])")


    fun stringEmpty(): Boolean {

        val state: Boolean = value.isEmpty()

        return if (state) {

            editText.error = "Field can't be empty"
            false

        } else {

            true

        }

    }

    fun doesNotContainsSpecialCharacter(): Boolean {

        Log.d("special characters", value.contains(atLeastOneSpecialCharacter).toString())
        val state = value.contains(atLeastOneSpecialCharacter)

        return if (state) {

            editText.error = "Field should not contain a special character"
            false

        } else {

            true

        }

    }


    fun phoneNumber(): Boolean {

        val state = value.matches(phoneNumberPattern)

        return if (state) {

            true


        } else {

            editText.error = "Invalid phone number"
            false

        }

    }

    fun validateEmail(): Boolean{

        Log.d("email", value)
        val state = emailPattern.matches(value)

        return if (state){
            state
        } else {
            editText.error = "Invalid email!"
            false
        }

    }


    fun noWhiteSpaces(): Boolean {

        Log.d("Spaces", value.contains(containWhiteSpace).toString())
        val state = value.contains(containWhiteSpace)

        return if (state) {

            true

        } else {

            editText.error = "Field should not contain spaces"
            false

        }
    }

}