package com.example.auth.presentation

import com.example.designsystem.components.TextFieldState
import com.example.designsystem.components.textFieldStateSaver

class LoginState : TextFieldState(::isLoginValid, ::loginValidationError)

private fun isLoginValid(login: String) = login.isNotEmpty()

private fun loginValidationError(login: String): String = "Invalid login"

val LoginStateSaver = textFieldStateSaver(LoginState())