package com.interview.googleplacespoc.data

import com.interview.googleplacespoc.data.entity.User

interface DatabaseHelper {

    suspend fun getUsers(): List<User>

    suspend fun insertAll(users: List<User>)

    suspend fun deleteAll(users: List<User>)

}