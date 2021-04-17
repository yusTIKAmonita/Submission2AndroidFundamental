package com.example.mysubmission2.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import java.sql.SQLException

class FavoriteHelper(context: Context) {

    private var databaseHelper : DatabaseHelper = DatabaseHelper(context)
    private lateinit var database: SQLiteDatabase

    companion object {
        private const val DATABASE_TABLE = DatabaseFavorite.FavColumns.TABLE_FAVORITE
        private var INSTANCE : FavoriteHelper? = null

        fun getInstance (context: Context): FavoriteHelper = INSTANCE?: synchronized(this) {
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
            "${DatabaseFavorite.FavColumns.USERNAME} DESC",
            null
        )
    }

    // Mengambil data dengan id tertentu
    fun queryById(id: String): Cursor {
        return database.query(
            DATABASE_TABLE,
            null,
            "${DatabaseFavorite.FavColumns.USERNAME} =?",
            arrayOf(id),
            null,
            null,
            null,
            null
        )
    }

    // Metode untuk menyimpan data
    fun insert (values: ContentValues?) : Long {
        return database.insert(DATABASE_TABLE, null, values)
    }

    // Metode untuk memperbaharui data
    fun update (id: String, values: ContentValues?): Int {
        return database.update(DATABASE_TABLE, values, "${DatabaseFavorite.FavColumns.USERNAME} = ?", arrayOf(id))
    }

    // Metode untuk menghapus data
    fun deleteById (id: String) : Int {
        return database.delete(DATABASE_TABLE, "${DatabaseFavorite.FavColumns.USERNAME} = '$id", null)
    }
}