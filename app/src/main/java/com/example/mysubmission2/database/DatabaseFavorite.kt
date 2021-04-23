package com.example.mysubmission2.database

import android.net.Uri
import android.provider.BaseColumns

internal class DatabaseFavorite {
//    const val AUTHORITY = "com.example.mysubmission2"
//    const val SCHEME = "content"

    internal class FavColumns: BaseColumns {
        companion object {
            const val TABLE_FAVORITE = "favorite"
            const val ID = "id"
            const val USERNAME = "username"
            const val NAME = "name"
            const val USER_LOCATION = "userlocation"
            const val PHOTO_URL = "photourl"
            const val PUBLIC_REPOS = "publicrepos"
            const val FOLLOWERS = "followers"
            const val FOLLOWING = "following"
            const val FAVORITE = "favorite"

//            val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
//                    .authority(AUTHORITY)
//                    .appendPath(TABLE_FAVORITE)
//                    .build()

        }
    }
}