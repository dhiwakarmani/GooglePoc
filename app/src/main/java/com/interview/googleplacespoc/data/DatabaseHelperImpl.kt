package com.interview.googleplacespoc.data

import com.interview.googleplacespoc.data.entity.User

class DatabaseHelperImpl(private val appDatabase: AdFoneDatabase) : DatabaseHelper {

    override suspend fun getUsers(): List<User> = appDatabase.userDao().getAll()

    override suspend fun insertAll(users: List<User>) = appDatabase.userDao().insertAll(users)

    override suspend fun deleteAll(users: List<User>) = appDatabase.userDao().deleteAll(users)

}