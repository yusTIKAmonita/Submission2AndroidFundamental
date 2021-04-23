package com.example.mysubmission2.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mysubmission2.UserData
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray

@SuppressLint("StaticFieldLeak")
class FollowViewModel(application: Application) : AndroidViewModel(application) {


    val userDataFollow = MutableLiveData<ArrayList<UserData>>()

    fun setUserFollow (username: String, tab: String) {

        val url = "https://api.github.com/users/$username/$tab"

        val listUserData = ArrayList<UserData>()
        val apiKey = "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"

        val client = AsyncHttpClient()

        client.addHeader("Authorization", "token $apiKey")
        client.addHeader("User-Agent", "request")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<out Header>, responseBody: ByteArray) {
                try {
                    val result = String(responseBody)
                    val jsonArray = JSONArray(result)
                    for (i in 0 until jsonArray.length()) {
                        val gitHubUsers = jsonArray.getJSONObject(i)
                        val listUser = UserData()
                        listUser.username = gitHubUsers.getString("login")
                        listUser.type = gitHubUsers.getString("type")
                        listUser.photoUrl = gitHubUsers.getString("avatar_url")
                        listUserData.add(listUser)
                    }
                    userDataFollow.postValue(listUserData)
                } catch(e: Exception) {
                }

            }

            @SuppressLint("ShowToast")
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
    internal fun getUserFollow(): LiveData<ArrayList<UserData>> {
        return userDataFollow
    }
}