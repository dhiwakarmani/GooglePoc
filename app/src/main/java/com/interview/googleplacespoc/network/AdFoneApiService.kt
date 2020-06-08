package com.interview.googleplacespoc.network

import com.interview.googleplacespoc.model.AdFoneUser
import retrofit2.http.GET

interface AdFoneApiService {

    @GET("places")
    suspend fun getUsers(): List<AdFoneUser>

    @GET("more-users")
    suspend fun getMoreUsers(): List<AdFoneUser>

    @GET("error")
    suspend fun getUsersWithError(): List<AdFoneUser>

}