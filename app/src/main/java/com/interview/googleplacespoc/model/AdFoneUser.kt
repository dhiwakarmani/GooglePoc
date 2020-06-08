package com.interview.googleplacespoc.model

import com.google.gson.annotations.SerializedName

data class AdFoneUser(
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("latitude")
    val latitude: String = "",
    @SerializedName("longitude")
    val longitude: String = "",
    @SerializedName("avatar")
    val avatar: String = "",
    @SerializedName("rating")
    val rating: String = ""
)