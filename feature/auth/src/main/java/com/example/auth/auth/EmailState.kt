package com.example.auth.auth

import com.example.common.components.TextFieldState
import java.util.regex.Pattern

private const val EMAIL_VALIDATION_REGEX = "^(.+)@(.+)\$"
class EmailState : TextFieldState(::isLoginValid, ::loginValidationError)

private fun isLoginValid(email: String) = Pattern.matches(EMAIL_VALIDATION_REGEX, email)

private fun loginValidationError(login: String): String = "Invalid email"
