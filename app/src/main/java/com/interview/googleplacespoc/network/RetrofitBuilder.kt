package com.interview.googleplacespoc.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {

    private const val BASE_URL = "https://5edda45dae06ed0016ee36b2.mockapi.io/googleplaces/"

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiService: AdFoneApiService = getRetrofit().create(AdFoneApiService::class.java)

}