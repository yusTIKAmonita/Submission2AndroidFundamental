package com.example.mysubmission2.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns._ID
import android.util.Log
import com.example.mysubmission2.FavoriteData
import com.example.mysubmission2.database.DatabaseFavorite.FavColumns.Companion.NAME
import com.example.mysubmission2.database.DatabaseFavorite.FavColumns.Companion.TABLE_FAVORITE
import com.example.mysubmission2.database.DatabaseFavorite.FavColumns.Companion.USERNAME
import java.sql.SQLException

class FavoriteHelper(context: Context) {

    private var databaseHelper : DatabaseHelper = DatabaseHelper(context)
    private lateinit var database: SQLiteDatabase

    companion object {
        private const val DATABASE_TABLE = TABLE_FAVORITE
        private var INSTANCE : FavoriteHelper? = null

        fun getInstance(context: Context): FavoriteHelper =
                INSTANCE?: synchronized(this) {
                    INSTANCE?: FavoriteHelper(context)
                }

    }

    // Membuka koneksi ke database
    @Throws(SQLException::class)
    fun open() {
        database = databaseHelper.writableDatabase
    }

    //Menutup koneksi ke database
    fun close() {
        databaseHelper.close()

        if (database.isOpen)
            database.close()
    }

    // Mengambil data di database
    fun queryAll(): Cursor {
        return database.query(
            DATABASE_TABLE,
            null,
            null,
            null,
            null,
            null,
            "$NAME DESC",
            null
        )
    }

    // Mengambil data dengan id tertentu
    fun queryById(id: String): Cursor {
        return database.query(
            DATABASE_TABLE,
            null,
            "$USERNAME =?",
            arrayOf(id),
            null,
            null,
            null,
        )
    }

    // Metode untuk menyimpan data
    fun insert (values: ContentValues?) : Long {
        return database.insert(DATABASE_TABLE, null, values)
    }

    // Metode untuk memperbaharui data
    fun update (id: String, values: ContentValues?): Int {
        return database.update(DATABASE_TABLE, values, "$_ID = ?", arrayOf(id))
    }

    // Metode untuk menghapus data
    fun deleteById (id: String) : Int {
        return database.delete(DATABASE_TABLE, "$USERNAME = '$id'", null)
    }

    fun getAllFavorite(): ArrayList<FavoriteData> {
        val arrayList = ArrayList<FavoriteData>()
        val cursor = database.query(DATABASE_TABLE, null, null, null, null, null, "$USERNAME ASC", null)

        cursor.moveToFirst()
        var favoriteData:FavoriteData

        if (cursor.count >0) {
            do {
                favoriteData = FavoriteData()
                favoriteData.username = cursor.getString(cursor.getColumnIndexOrThrow(USERNAME))
                // Ada tambahan

                arrayList.add(favoriteData)
                cursor.moveToFirst()
            } while (!cursor.isAfterLast)
        }

        cursor.close()
        return arrayList
    }

//    Check Id
//    fun checkId (id: String): Boolean {
//        val cursor = database.query(DATABASE_TABLE,
//                null,
//                "$USERNAME =?",
//                arrayOf(id), null, null, null)
//        var check = false
//        if(cursor.moveToFirst()) {
//            check = true
//            var cursorIndex = 0
//            while (cursor.moveToNext()) cursorIndex++
//            Log.d(TAG, "Username Found $cursorIndex")
//        }
//        cursor.close()
//        return check
//    }
}