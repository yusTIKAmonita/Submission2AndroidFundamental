package com.example.mysubmission2.viewmodel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mysubmission2.UserData
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Exception

class SearchViewModel(application: Application) : AndroidViewModel(application) {

    val userDataSearch = MutableLiveData<ArrayList<UserData>>()

    fun setUserSearch (username: String) {
        val ListUser = ArrayList<UserData>()

        val apiKey = "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"
        val url = "https://api.github.com/search/users?q=$username"

        val client = AsyncHttpClient()

        client.addHeader("Authorization", "token $apiKey")
        client.addHeader("User-Agent", "request")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<out Header>, responseBody: ByteArray) {
                try {
                    val result = String(responseBody)
                    val jsonObject = JSONObject(result)
                    val jsonArray = jsonObject.getJSONArray("items")
                    for (i in 0 until jsonArray.length()) {
                        val gitHubUsers = jsonArray.getJSONObject(i)
                        val listUser = UserData()
                        listUser.username = gitHubUsers.getString("login")
                        listUser.type = gitHubUsers.getString("type")
                        listUser.photoUrl = gitHubUsers.getString("avatar_url")
                        ListUser.add(listUser)
                    }
                    userDataSearch.postValue(ListUser)
                } catch(e: Exception) {
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
    internal fun getUserSearch(): LiveData<ArrayList<UserData>> {
        return userDataSearch
    }
}