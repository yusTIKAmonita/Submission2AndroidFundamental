//package com.example.mysubmission2
//
//import android.content.ContentProvider
//import android.content.ContentValues
//import android.content.Context
//import android.content.UriMatcher
//import android.database.Cursor
//import android.net.Uri
//import com.example.mysubmission2.database.DatabaseFavorite.AUTHORITY
//import com.example.mysubmission2.database.DatabaseFavorite.FavColumns.Companion.CONTENT_URI
//import com.example.mysubmission2.database.DatabaseFavorite.FavColumns.Companion.TABLE_FAVORITE
//import com.example.mysubmission2.database.FavoriteHelper
//
//class FavoriteProvider: ContentProvider() {
//
//    companion object {
//        private const val FAV = 1
//        private const val FAV_USERNAME = 2
//        private lateinit var favoriteHelper: FavoriteHelper
//        private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)
//
//        init {
//            uriMatcher.addURI(AUTHORITY, TABLE_FAVORITE, FAV)
//            uriMatcher.addURI(AUTHORITY, "$TABLE_FAVORITE/#", FAV_USERNAME)
//        }
//    }
//    override fun onCreate(): Boolean {
//        favoriteHelper = FavoriteHelper.getInstance(context as Context)
//        favoriteHelper.open()
//        return true
//    }
//
//    override fun query(uri: Uri, projection: Array<String>?, selection: String?, selectionArgs: Array<String>?, sortOrder: String?): Cursor? {
//        return when (uriMatcher.match(uri)) {
//            FAV -> favoriteHelper.queryAll()
//            FAV_USERNAME -> favoriteHelper.queryById(uri.lastPathSegment.toString())
//            else -> null
//        }
//    }
//
//    override fun getType(uri: Uri): String? {
//        return null
//    }
//
//    override fun insert(uri: Uri, values: ContentValues?): Uri? {
//        val added: Long = when (FAV) {
//            uriMatcher.match(uri) -> favoriteHelper.insert(values)
//            else -> 0
//        }
//        context?.contentResolver?.notifyChange(CONTENT_URI, null)
//        return Uri.parse("$CONTENT_URI/$added")
//    }
//
//    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
//        val delete: Int = when (FAV_USERNAME) {
//            uriMatcher.match(uri) -> favoriteHelper.deleteById(uri.lastPathSegment.toString())
//            else -> 0
//        }
//
//        context?.contentResolver?.notifyChange(CONTENT_URI, null)
//        return delete
//    }
//
//    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<String>?): Int {
//        val update: Int = when (FAV_USERNAME) {
//            uriMatcher.match(uri) -> favoriteHelper.update(uri.lastPathSegment.toString(), values)
//            else -> 0
//        }
//        context?.contentResolver?.notifyChange(CONTENT_URI, null)
//        return update
//    }
//
//}