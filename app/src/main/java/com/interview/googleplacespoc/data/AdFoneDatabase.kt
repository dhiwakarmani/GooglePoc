package com.interview.googleplacespoc.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.interview.googleplacespoc.data.dao.UserDao
import com.interview.googleplacespoc.data.entity.User

@Database(entities = [User::class], version = 1)
abstract class AdFoneDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

}