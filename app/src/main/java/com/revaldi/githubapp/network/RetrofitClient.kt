package com.revaldi.githubapp.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const final val BASE_URL = "https://api.github.com/"
    val retrofit = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()
    val apiInstance = retrofit.create(Api::class.java)
}