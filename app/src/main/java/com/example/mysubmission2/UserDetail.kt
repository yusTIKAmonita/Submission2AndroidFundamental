package com.example.mysubmission2

data class UserDetail (
        var username: String? = null,
        var name: String? = null,
        var userLocation: String? = null,
        var photoUrl: String? = null,
        var publicRepos: Int? = null,
        var followers: Int? = null,
        var following: Int? = null
)