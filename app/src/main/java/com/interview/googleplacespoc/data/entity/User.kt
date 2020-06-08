package com.interview.googleplacespoc.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "latitude") val latitude: String?,
    @ColumnInfo(name = "longitude") val longitude: String?,
    @ColumnInfo(name = "avatar") val avatar: String?,
    @ColumnInfo(name = "rating") val rating: String?,
    @ColumnInfo(name = "radius") val radius: String
)