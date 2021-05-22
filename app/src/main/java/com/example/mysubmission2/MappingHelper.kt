package com.example.mysubmission2

import android.database.Cursor
import com.example.mysubmission2.data.FavoriteData
import com.example.mysubmission2.database.DatabaseFavorite
import com.example.mysubmission2.database.DatabaseFavorite.FavColumns.Companion._ID

//import com.example.mysubmission2.database.DatabaseFavorite.FavColumns.Companion.ID

object MappingHelper {
    fun mapCursorToArrayList (favoriteCursor : Cursor?) : ArrayList<FavoriteData> {
        val favoriteList = ArrayList<FavoriteData>()

        favoriteCursor?.apply {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(_ID))
                val username = getString(getColumnIndexOrThrow(DatabaseFavorite.FavColumns.USERNAME))
                val name = getString(getColumnIndexOrThrow(DatabaseFavorite.FavColumns.NAME))
                val location = getString(getColumnIndexOrThrow(DatabaseFavorite.FavColumns.USER_LOCATION))
                val photo = getString(getColumnIndexOrThrow(DatabaseFavorite.FavColumns.PHOTO_URL))
                val repository = getString(getColumnIndexOrThrow(DatabaseFavorite.FavColumns.PUBLIC_REPOS))
//                val followers = getString(getColumnIndexOrThrow(DatabaseFavorite.FavColumns.FOLLOWERS))
//                val following = getString(getColumnIndexOrThrow(DatabaseFavorite.FavColumns.FOLLOWING))
                val favorite = getString(getColumnIndexOrThrow(DatabaseFavorite.FavColumns.FAVORITE))

                favoriteList.add (
                        FavoriteData(
                                id,
                                username,
                                name,
                                location,
                                photo,
                                repository,
//                            followers,
//                            following,
                                favorite
                        )
                )
            }
        }
        return favoriteList
    }

    fun mapCursorToObject (favoriteCursor : Cursor?) : FavoriteData {
        var favoriteList = FavoriteData()

        favoriteCursor?.apply {
            moveToFirst()
                val id = getInt(getColumnIndexOrThrow(_ID))
                val username = getString(getColumnIndexOrThrow(DatabaseFavorite.FavColumns.USERNAME))
                val name = getString(getColumnIndexOrThrow(DatabaseFavorite.FavColumns.NAME))
                val location = getString(getColumnIndexOrThrow(DatabaseFavorite.FavColumns.USER_LOCATION))
                val photo = getString(getColumnIndexOrThrow(DatabaseFavorite.FavColumns.PHOTO_URL))
                val repository = getString(getColumnIndexOrThrow(DatabaseFavorite.FavColumns.PUBLIC_REPOS))
//                val followers = getString(getColumnIndexOrThrow(DatabaseFavorite.FavColumns.FOLLOWERS))
//                val following = getString(getColumnIndexOrThrow(DatabaseFavorite.FavColumns.FOLLOWING))
                val favorite = getString(getColumnIndexOrThrow(DatabaseFavorite.FavColumns.FAVORITE))

                favoriteList =
                    FavoriteData(
                        id,
                        username,
                        name,
                        location,
                        photo,
                        repository,
//                            followers,
//                            following,
                        favorite

                )

        }
        return favoriteList
    }
}