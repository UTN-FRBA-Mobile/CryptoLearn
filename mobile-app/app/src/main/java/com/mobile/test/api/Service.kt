package com.mobile.test.api

import com.mobile.test.model.LoginRequest
import com.mobile.test.model.LoginResponse
import retrofit2.Call
import retrofit2.http.*

interface Service {
    @Headers("Content-Type: application/json")
    @POST("log_in")
    fun login(
        @Body loginRequest: LoginRequest
    ): Call<LoginResponse>
}