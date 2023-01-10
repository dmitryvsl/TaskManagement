package com.example.dashboard

import com.example.designsystem.components.textfield.TextFieldState

class SearchState : TextFieldState(::searchValidation, ::searchError)

private fun searchValidation(query: String) = query.isNotBlank()

private fun searchError(query: String) = "Search query must not be blank!"