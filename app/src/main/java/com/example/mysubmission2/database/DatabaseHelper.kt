package com.example.mysubmission2.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.mysubmission2.database.DatabaseFavorite.FavColumns.Companion.TABLE_NAME
import com.example.mysubmission2.database.DatabaseFavorite.FavColumns.Companion._ID

internal class DatabaseHelper (context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "dbfavoriteuser15"

        private const val DATABASE_VERSION = 1

        private const val SQL_CREAT_TABLE_FAVORITE = "CREATE TABLE $TABLE_NAME" +
                "($_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
//                "(${USERNAME} TEXT UNIQUE NOT NULL," +
                "${DatabaseFavorite.FavColumns.USERNAME} TEXT NOT NULL," +
                "${DatabaseFavorite.FavColumns.NAME} TEXT NOT NULL," +
                "${DatabaseFavorite.FavColumns.USER_LOCATION} TEXT NOT NULL," +
                "${DatabaseFavorite.FavColumns.PHOTO_URL} TEXT NOT NULL," +
                "${DatabaseFavorite.FavColumns.PUBLIC_REPOS} TEXT NOT NULL," +
                "${DatabaseFavorite.FavColumns.FAVORITE} TEXT NOT NULL)"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREAT_TABLE_FAVORITE)
    }

    override fun onUpgrade (db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
}