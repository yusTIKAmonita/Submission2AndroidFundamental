package com.example.mysubmission2.viewmodel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mysubmission2.data.FavoriteData
import com.example.mysubmission2.MappingHelper
import com.example.mysubmission2.data.UserDetail
import com.example.mysubmission2.database.DatabaseFavorite
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.lang.Exception

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {

//    companion object {
//        private val TAG = FavoriteViewModel::class.java.simpleName
//    }
//
//    private val listFavorite = MutableLiveData<ArrayList<FavoriteData>>()
//
//    fun setUserFavorite(context: String?) {
//        GlobalScope.launch (Dispatchers.Main) {
//            try {
//                val defferedFavorite = async(Dispatchers.IO) {
//                    val cursor = context.contentResolver.query(DatabaseFavorite.FavColumns.CONTENT_URI,
//                            null,
//                            null,
//                            null,
//                            null)
//                    MappingHelper.mapCursorToArrayList(cursor)
//                }
//                val favDat = defferedFavorite.await()
//                Log.d (TAG, "$favDat")
//                listFavorite.postValue(favDat)
//                Log.d(TAG, "$listFavorite")
//            } catch (e:Exception) {
//                Log.d(TAG, e.message.toString())
//                Toast.makeText(context, "Error: ${e.message.toString()}", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }
//
//
//
//    fun getUserFavorite() : LiveData<ArrayList<FavoriteData>> = listFavorite

    val userDataSearch = MutableLiveData<FavoriteData>()

    fun setUserFavorite(username: String) {

        val apiKey = "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"
        val url = "https://api.github.com/users/$username"

        val client = AsyncHttpClient()

        client.addHeader("Authorization", "token $apiKey")
        client.addHeader("User-Agent", "request")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<out Header>, responseBody: ByteArray) {
                try {
                    val result = String(responseBody)
                    val jsonObject = JSONObject(result)

                    val listUser = FavoriteData()
                    listUser.username = jsonObject.getString("login")
                    listUser.name = jsonObject.getString("name")
                    listUser.photoUrl = jsonObject.getString("avatar_url")
                    listUser.userLocation = jsonObject.getString("location")
                    listUser.publicRepos = jsonObject.getString("public_repos")
                    listUser.followers = jsonObject.getString("followers")
                    listUser.following = jsonObject.getString("following")

                    userDataSearch.postValue(listUser)
                } catch (e: Exception) {
                    e.message
                }

            }

            override fun onFailure(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray?, error: Throwable?) {
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode: Bad request"
                    403 -> "$statusCode: Forbidden"
                    404 -> "$statusCode: NotFound"
                    else -> "$statusCode: ${error?.message}"
                }
                Toast.makeText(getApplication(), errorMessage, Toast.LENGTH_SHORT)
            }
        })

    }

    internal fun getUserFavorite(): MutableLiveData<FavoriteData> {
        return userDataSearch
    }
}