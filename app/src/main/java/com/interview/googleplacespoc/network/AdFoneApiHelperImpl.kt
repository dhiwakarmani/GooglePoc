package com.interview.googleplacespoc.network

class AdFoneApiHelperImpl(private val apiService: AdFoneApiService) : AdFoneApiHelper {

    override suspend fun getUsers() = apiService.getUsers()

    override suspend fun getMoreUsers() = apiService.getMoreUsers()

    override suspend fun getUsersWithError() = apiService.getUsersWithError()

}