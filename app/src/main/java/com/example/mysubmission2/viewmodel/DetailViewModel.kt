package com.example.mysubmission2.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.mysubmission2.data.UserDetail
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject
import java.lang.Exception

class DetailViewModel(application: Application) : AndroidViewModel(application) {

    val userDataSearch = MutableLiveData<UserDetail>()

    fun setUserDetail(username: String) {

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

                    val listUser = UserDetail()
                    listUser.username = jsonObject.getString("login")
                    listUser.name = jsonObject.getString("name")
                    listUser.photoUrl = jsonObject.getString("avatar_url")
                    listUser.userLocation = jsonObject.getString("location")
                    listUser.publicRepos = jsonObject.getInt("public_repos")
                    listUser.followers = jsonObject.getInt("followers")
                    listUser.following = jsonObject.getInt("following")

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

    internal fun getUserDetail(): MutableLiveData<UserDetail> {
        return userDataSearch
    }
}