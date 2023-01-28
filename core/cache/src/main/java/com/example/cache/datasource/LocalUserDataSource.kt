package com.example.cache.datasource

interface LocalUserDataSource {

    fun saveToken(token: String)
    fun isTokenSaved(): Boolean
    fun getToken(): String

}