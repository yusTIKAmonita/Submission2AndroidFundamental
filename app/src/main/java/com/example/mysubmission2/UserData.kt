package com.example.mysubmission2

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
class UserData (
    var username: String? = null,
    var type: String? = null,
    var photoUrl: String? = null
) :Parcelable
