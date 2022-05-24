package com.mobile.test.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val BASE_URL = "http://localhost:8080"

    val service = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("https://us-central1-clases-854bb.cloudfunctions.net/list/")
        .build()
        .create(Service::class.java)
}