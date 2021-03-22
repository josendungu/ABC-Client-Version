package com.android.abc.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Client(
    val surname: String,
    val firstName: String,
    val lastName:String,
    val phoneNumber: Int?,
    val email: String,
    val county: String,
    val town: String,
    val id: Int?,
    val plates: MutableList<String>
): Parcelable