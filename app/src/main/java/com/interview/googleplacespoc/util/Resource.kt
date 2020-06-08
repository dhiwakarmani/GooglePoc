package com.interview.googleplacespoc.util

import java.lang.Math.*

data class Resource<out T>(val status: Status, val data: T?, val message: String?) {

    companion object {

        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(msg: String, data: T?): Resource<T> {
            return Resource(Status.ERROR, data, msg)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }

        val currentLocationLatitude = 25.724338
        val currentLocationLongitude = -80.4646734

        fun findRadius(
            currentLocationLatitude: Double,
            currentLocationLongitude: Double,
            latitude: Double,
            longitude: Double
        ): Double {
            return 6371 * acos(
                sin(currentLocationLatitude) * sin(latitude)
                        + cos(currentLocationLatitude) * cos(latitude) * cos(
                    currentLocationLongitude - longitude
                )
            )
        }

        fun ratingUtility(value: String) : String {
            return value[0].toString()
        }

    }

}