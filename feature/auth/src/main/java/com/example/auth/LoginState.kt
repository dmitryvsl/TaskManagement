package com.example.auth

import com.example.common.components.TextFieldState

class LoginState : TextFieldState(::isLoginValid, ::loginValidationError)

private fun isLoginValid(login: String) = login.isNotEmpty()

private fun loginValidationError(login: String): String = "Invalid login"
