package com.example.taskmanagement.utils

import android.content.SharedPreferences
import javax.inject.Inject

const val sharedPreferences = "shared_preferences"
private const val isFirstLaunch = "is_first_launch"

class SharedPreferencesUtils @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {

    fun isFirstLaunch(): Boolean = sharedPreferences.getBoolean(isFirstLaunch, true)

    fun markOnboardingPassed() {
        with(sharedPreferences.edit()) {
            putBoolean(isFirstLaunch, false)
            apply()
        }
    }
}