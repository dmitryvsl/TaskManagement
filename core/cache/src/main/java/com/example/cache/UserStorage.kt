package com.example.cache

import android.content.Context
import com.example.cache.datasource.LocalUserDataSource
import javax.inject.Inject

private const val TOKEN = "token"
private const val PREFERENCE_NAME = "TaskManagement"

class UserStorage @Inject constructor(
    private val context: Context
) : LocalUserDataSource {

    private val prefs by lazy { context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE) }

    override fun saveToken(token: String) {
        prefs.edit().apply {
            putString(TOKEN, token)
            apply()
        }
    }

    override fun isTokenSaved(): Boolean = prefs.getString(TOKEN, null) != null

    override fun getToken(): String = prefs.getString(TOKEN, null)!!

}