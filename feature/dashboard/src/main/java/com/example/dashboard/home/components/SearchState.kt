package com.example.dashboard.home.components

import com.example.dashboard.home.searchError
import com.example.dashboard.home.searchValidation
import com.example.designsystem.components.textfield.TextFieldState

class SearchState : TextFieldState(::searchValidation, ::searchError)