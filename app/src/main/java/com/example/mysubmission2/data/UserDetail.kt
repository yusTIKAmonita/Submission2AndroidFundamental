package com.example.mysubmission2.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserDetail (
        var username: String? = null,
        var name: String? = null,
        var userLocation: String? = null,
        var photoUrl: String? = null,
        var publicRepos: Int? = null,
        var followers: Int? = null,
        var following: Int? = null,
        var favorite: String? = null
) : Parcelable