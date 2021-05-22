package com.example.mysubmission2.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
class UserData (
    var username: String? = null,
    var type: String? = null,
    var photoUrl: String? = null,
    var favorite: String? = null
) :Parcelable
