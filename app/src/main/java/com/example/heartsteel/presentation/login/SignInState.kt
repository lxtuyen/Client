package com.example.heartsteel.presentation.login

data class SignInState(
    val isLoading: Boolean = false,
    val isSuccess: String = "",
    val isError: String? = ""
)