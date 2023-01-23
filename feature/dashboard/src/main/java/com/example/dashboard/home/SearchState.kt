package com.example.dashboard.home

internal fun searchValidation(query: String) = query.isNotBlank()

internal fun searchError(query: String) = "Search query must not be blank!"