package com.mobile.test.api

import com.mobile.test.model.Login.LoginRequest
import com.mobile.test.model.Login.LoginResponse
import retrofit2.Call
import retrofit2.http.*

interface Service {
    @Headers("Content-Type: application/json")
    @POST("log_in")
    fun login(
        @Body loginRequest: LoginRequest
    ): Call<LoginResponse>
}