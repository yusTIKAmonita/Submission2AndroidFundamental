package com.example.mysubmission2.database

import android.net.Uri
import android.provider.BaseColumns

object DatabaseFavorite {

    const val AUTHORITY = "com.example.mysubmission2"
    const val SCHEME = "content"

    internal class FavColumns: BaseColumns {
        companion object {
            const val TABLE_NAME = "favorite"
            const val _ID = "id"
            const val USERNAME = "username"
            const val NAME = "name"
            const val USER_LOCATION = "userLocation"
            const val PHOTO_URL = "photoUrl"
            const val PUBLIC_REPOS = "publicRepos"
//            const val FOLLOWERS = "followers"
//            const val FOLLOWING = "following"
            const val FAVORITE = "favorite"

            val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
                    .authority(AUTHORITY)
                    .appendPath(TABLE_NAME)
                    .build()

        }
    }
}