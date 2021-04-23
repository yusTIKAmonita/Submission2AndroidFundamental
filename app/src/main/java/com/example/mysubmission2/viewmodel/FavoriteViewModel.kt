//package com.example.mysubmission2.viewmodel
//
//import android.content.Context
//import android.util.Log
//import android.widget.Toast
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//import com.example.mysubmission2.FavoriteData
//import com.example.mysubmission2.MappingHelper
//import com.example.mysubmission2.database.DatabaseFavorite
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.GlobalScope
//import kotlinx.coroutines.async
//import kotlinx.coroutines.launch
//import java.lang.Exception
//
//class FavoriteViewModel: ViewModel() {
//
//    companion object {
//        private val TAG = FavoriteViewModel::class.java.simpleName
//    }
//
//    private val listFavorite = MutableLiveData<ArrayList<FavoriteData>>()
//
////    fun setUserFavorite(context: Context) {
////        GlobalScope.launch (Dispatchers.Main) {
////            try {
////                val defferedFavorite = async(Dispatchers.IO) {
////                    val cursor = context.contentResolver.query(DatabaseFavorite.FavColumns.CONTENT_URI,
////                            null,
////                            null,
////                            null,
////                            null)
////                    MappingHelper.mapCursorToArrayList(cursor)
////                }
////                val favDat = defferedFavorite.await()
////                Log.d (TAG, "$favDat")
////                listFavorite.postValue(favDat)
////                Log.d(TAG, "$listFavorite")
////            } catch (e:Exception) {
////                Log.d(TAG, e.message.toString())
////                Toast.makeText(context, "Error: ${e.message.toString()}", Toast.LENGTH_SHORT).show()
////            }
////        }
////    }
//
//
//
//    fun getUserFavorite() : LiveData<ArrayList<FavoriteData>> = listFavorite
//}