package com.mobile.test.api

import retrofit2.Call
import retrofit2.http.*

interface Service {
    @Headers("Content-Type: application/json",)
    @POST("log_in")
    fun login(
        @Body loginRequest: LoginRequest
    ): Call<LoginResponse>

    @Headers("Content-Type: application/json",)
    @POST("sign_up")
    fun signup(
        @Body signupRequest: SignupRequest
    ): Call<String>

    @Headers("Content-Type: application/json",)
    @POST("reset_password")
    fun reset_password(
        @Body resetPasswordRequest: ResetPasswordRequest
    ): Call<String>

    @Headers("Content-Type: application/json")
    @GET("levels")
    fun getLevels(@Header("Authorization") token: String): Call<LevelsResponse>

    @Headers("Content-Type: application/json")
    @POST("levels/{level_index}/{chapter_index}")
    fun updateChapterStatus(
        @Header("Authorization") token: String,
        @Path("level_index") level_index:  Int,
        @Path("chapter_index") chapter_index:  Int,
        @Body updateChapterStatusRequest: UpdateChapterStatusRequest
    ): Call<String>
}