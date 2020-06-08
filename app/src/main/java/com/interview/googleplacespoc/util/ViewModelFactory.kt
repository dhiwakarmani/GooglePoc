package com.interview.googleplacespoc.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.interview.googleplacespoc.data.DatabaseHelper
import com.interview.googleplacespoc.network.AdFoneApiHelper
import com.interview.googleplacespoc.ui.room.AdFoneRoomDBViewModel

class ViewModelFactory(private val apiHelper: AdFoneApiHelper, private val dbHelper: DatabaseHelper) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AdFoneRoomDBViewModel::class.java)) {
            return AdFoneRoomDBViewModel(apiHelper, dbHelper) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}