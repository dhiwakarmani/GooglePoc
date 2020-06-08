package com.interview.googleplacespoc.ui.room

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.interview.googleplacespoc.data.DatabaseHelper
import com.interview.googleplacespoc.data.entity.User
import com.interview.googleplacespoc.network.AdFoneApiHelper
import com.interview.googleplacespoc.util.Resource
import com.interview.googleplacespoc.util.Resource.Companion.currentLocationLatitude
import com.interview.googleplacespoc.util.Resource.Companion.currentLocationLongitude
import com.interview.googleplacespoc.util.Resource.Companion.findRadius
import com.interview.googleplacespoc.util.Resource.Companion.ratingUtility
import kotlinx.coroutines.launch
import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.sin

class AdFoneRoomDBViewModel(private val apiHelper: AdFoneApiHelper, private val dbHelper: DatabaseHelper) :
    ViewModel() {

    private val users = MutableLiveData<Resource<List<User>>>()

    init {
        fetchUsers()
    }

    fun fetchUsers() {
        viewModelScope.launch {
            users.postValue(Resource.loading(null))
            try {
                val usersFromDb = dbHelper.getUsers()
                if (usersFromDb.isEmpty()) {
                    val usersFromApi = apiHelper.getUsers()
                    val usersToInsertInDB = mutableListOf<User>()

                    for (apiUser in usersFromApi) {
                        val radius = findRadius(currentLocationLatitude,
                            currentLocationLongitude,
                        apiUser.latitude.toDouble(),
                        apiUser.longitude.toDouble())
                        val user = User(
                            apiUser.id,
                            apiUser.name,
                            apiUser.latitude,
                            apiUser.longitude,
                            apiUser.avatar,
                            ratingUtility(apiUser.rating),
                            radius.toString()
                        )
                        usersToInsertInDB.add(user)
                    }

                    dbHelper.insertAll(usersToInsertInDB)

                    users.postValue(Resource.success(usersToInsertInDB))

                } else {
                    users.postValue(Resource.success(usersFromDb))
                }


            } catch (e: Exception) {
                users.postValue(Resource.error("Something Went Wrong", null))
            }
        }
    }

    fun getUsers(): LiveData<Resource<List<User>>> {
        return users
    }

}