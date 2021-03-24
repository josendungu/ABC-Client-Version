package com.android.abc.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Client(
    var surname: String? = null,
    var firstName: String? = null,
    var lastName:String? = null,
    var phoneNumber: Int? = null,
    var email: String? = null,
    var county: String? = null,
    var town: String? = null,
    var id: Int? = null,
    var plates: MutableList<String>? = null
): Parcelable