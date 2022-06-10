package com.mobile.test.api

import retrofit2.Call
import retrofit2.http.*

interface Service {
    @Headers(
        "Content-Type: application/json",
    )
    @POST("log_in")
    fun login(
        @Body loginRequest: LoginRequest
    ): Call<LoginResponse>

    @Headers("Content-Type: application/json")
    @GET("levels")
    fun getLevels(@Header("Authorization") token: String): Call<LevelsResponse>
}