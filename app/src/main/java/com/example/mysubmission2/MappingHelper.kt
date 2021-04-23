package com.example.mysubmission2

import android.database.Cursor
import android.provider.BaseColumns._ID
import com.example.mysubmission2.database.DatabaseFavorite

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
                val repository = getInt(getColumnIndexOrThrow(DatabaseFavorite.FavColumns.PUBLIC_REPOS))
                val favorite = getString(getColumnIndexOrThrow(DatabaseFavorite.FavColumns.FAVORITE))

                favoriteList.add (
                        FavoriteData(
                                id,
                                username,
                                name,
                                location,
                                photo,
                                repository,
                                favorite
                        )
                )
            }
        }
        return favoriteList
    }
}