package com.interview.googleplacespoc.network

import com.interview.googleplacespoc.model.AdFoneUser

interface AdFoneApiHelper {

    suspend fun getUsers(): List<AdFoneUser>

    suspend fun getMoreUsers(): List<AdFoneUser>

    suspend fun getUsersWithError(): List<AdFoneUser>

}