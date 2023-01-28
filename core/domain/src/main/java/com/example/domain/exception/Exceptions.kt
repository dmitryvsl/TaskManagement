package com.example.domain.exception

class UserAlreadyExist : Throwable()

class NoInternetException : Throwable()

class UserAuthException : Throwable()

class IncorrectDataException : Throwable()

class UserNotExist : Throwable()

/**
 * Throws when response from server is empty
 */
class InformationNotFound : Throwable()