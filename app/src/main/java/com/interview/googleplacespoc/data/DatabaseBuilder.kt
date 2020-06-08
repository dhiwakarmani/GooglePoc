package com.interview.googleplacespoc.data

import android.content.Context
import androidx.room.Room

object DatabaseBuilder {

    private var INSTANCE: AdFoneDatabase? = null

    fun getInstance(context: Context): AdFoneDatabase {
        if (INSTANCE == null) {
            synchronized(AdFoneDatabase::class) {
                INSTANCE = buildRoomDB(context)
            }
        }
        return INSTANCE!!
    }

    private fun buildRoomDB(context: Context) =
        Room.databaseBuilder(
            context.applicationContext,
            AdFoneDatabase::class.java,
            "adfone-interview"
        ).build()

}