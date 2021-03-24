package com.android.abc.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class ScheduleDetails(
    var surname: String? = null,
    var firstName: String? = null,
    var lastName: String? = null,
    var phoneNumber: String? = null,
    var email: String? = null,
    var id: String? = null,
    var plateNumber: String?= null,
    var county: String? = null,
    var town: String? = null,
    var locationDetails: String? = null,
    var day: String? = null,
    var time: String? = null,
    var assignedTo: String? = null
): Parcelable