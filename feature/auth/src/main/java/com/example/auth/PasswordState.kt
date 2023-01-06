package com.example.auth

import com.example.designsystem.components.TextFieldState

class PasswordState : TextFieldState(::isPasswordCorrect, ::passwordValidationError)

class ConfirmPasswordState(private val passwordState: PasswordState) : TextFieldState() {
    override val isValid
        get() = passwordAndConfirmationValid(passwordState.text, text)


    override fun getError(): String? {
        return if (showErrors()) {
            passwordConfirmationError()
        } else {
            null
        }
    }
}

private fun passwordAndConfirmationValid(password: String, confirmedPassword: String): Boolean {
    return isPasswordCorrect(password) && password == confirmedPassword
}


private fun isPasswordCorrect(password: String): Boolean = password.length >= 8

private fun passwordValidationError(password: String) =
    "Password should contain at least 8 characters"

private fun passwordConfirmationError(): String = "Passwords don't match"

