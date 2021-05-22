package com.example.mysubmission2.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class FavoriteData(
        val id: Int= 0,
        var username: String? = null,
        var name: String? = null,
        var userLocation: String? = null,
        var photoUrl: String? = null,
        var publicRepos: String? = null,
        var followers: String? = null,
        var following: String? = null,
        var favorite: String? = null
): Parcelable