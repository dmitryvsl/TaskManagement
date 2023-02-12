package com.example.settings.createworkspace

import com.example.designsystem.components.textfield.TextFieldState

class NameState : TextFieldState(::validate, ::errorFor) {
}

private fun validate(name: String) = name.isNotEmpty()

private fun errorFor(name: String) = "Empty field"