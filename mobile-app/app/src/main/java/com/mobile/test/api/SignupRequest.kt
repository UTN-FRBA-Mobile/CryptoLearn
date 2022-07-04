package com.mobile.test.api

data class SignupRequest (
    val name: String,
    val last_name: String,
    val email: String,
    val password: String
)