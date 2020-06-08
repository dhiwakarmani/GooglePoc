package com.interview.googleplacespoc

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.interview.googleplacespoc.data.DatabaseHelper
import com.interview.googleplacespoc.data.entity.User
import com.interview.googleplacespoc.model.AdFoneUser
import com.interview.googleplacespoc.network.AdFoneApiHelper
import com.interview.googleplacespoc.ui.room.AdFoneRoomDBViewModel
import com.interview.googleplacespoc.util.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class AdFoneRoomDBViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var apiHelper: AdFoneApiHelper

    @Mock
    private lateinit var databaseHelper: DatabaseHelper

    @Mock
    private lateinit var apiUsersObserver: Observer<Resource<List<User>>>

    @Before
    fun setUp() {
        // do something if required
    }

    @Test
    fun givenServerResponse200_whenFetch_shouldReturnSuccess() {
        testCoroutineRule.runBlockingTest {
            doReturn(emptyList<AdFoneUser>())
                .`when`(apiHelper)
                .getUsers()
            val viewModel = AdFoneRoomDBViewModel(apiHelper, databaseHelper)
            viewModel.getUsers().observeForever(apiUsersObserver)
            verify(apiHelper).getUsers()
            verify(apiUsersObserver).onChanged(Resource.success(emptyList()))
            viewModel.getUsers().removeObserver(apiUsersObserver)
        }
    }

    @Test
    fun givenServerResponseError_whenFetch_shouldReturnError() {
        testCoroutineRule.runBlockingTest {
            val errorMessage = "Error Message For You"
            doThrow(RuntimeException(errorMessage))
                .`when`(apiHelper)
                .getUsers()
            val viewModel = AdFoneRoomDBViewModel(apiHelper, databaseHelper)
            viewModel.getUsers().observeForever(apiUsersObserver)
            verify(apiHelper).getUsers()
            verify(apiUsersObserver).onChanged(
                Resource.error(
                    RuntimeException(errorMessage).toString(),
                    null
                )
            )
            viewModel.getUsers().removeObserver(apiUsersObserver)
        }
    }

    @After
    fun tearDown() {
        // do something if required
    }

}