package com.interview.googleplacespoc.ui.room

import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.interview.googleplacespoc.R
import com.interview.googleplacespoc.data.DatabaseBuilder
import com.interview.googleplacespoc.data.DatabaseHelperImpl
import com.interview.googleplacespoc.data.entity.User
import com.interview.googleplacespoc.network.AdFoneApiHelperImpl
import com.interview.googleplacespoc.network.RetrofitBuilder
import com.interview.googleplacespoc.util.Status
import com.interview.googleplacespoc.util.ViewModelFactory
import kotlinx.android.synthetic.main.activity_adfone.*

class AdFoneRoomActivity : AppCompatActivity() {

    private lateinit var viewModel: AdFoneRoomDBViewModel
    private lateinit var adapter: UserAdapter
    private var radius = 11.0
    var listOfUser = mutableListOf<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adfone)
        setupUI()
        setupViewModel()
        setupObserver()
    }

    private fun setupUI() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter =
            UserAdapter(
                arrayListOf()
            )
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                (recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        recyclerView.adapter = adapter
    }

    private fun setupObserver() {
        viewModel.getUsers().observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    progressBar.visibility = View.GONE
                    it.data?.let { users -> renderList(users) }
                    recyclerView.visibility = View.VISIBLE
                }
                Status.LOADING -> {
                    progressBar.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                }
                Status.ERROR -> {
                    //Handle Error
                    progressBar.visibility = View.GONE
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun renderList(users: List<User>) {
        for (user in users) {
            if(user.radius.toDouble() < radius) {
                listOfUser.add(user)
            }
        }
        if(listOfUser.isEmpty()) {
            adapter.addData(users)
        } else {
            adapter.addData(listOfUser)
        }

        adapter.notifyDataSetChanged()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(
                AdFoneApiHelperImpl(RetrofitBuilder.apiService),
                DatabaseHelperImpl(DatabaseBuilder.getInstance(applicationContext))
            )
        ).get(AdFoneRoomDBViewModel::class.java)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        val search = menu.findItem(R.id.action_search)
        val searchView = search.actionView as SearchView
        searchView.queryHint = "Search in Radius (miles)"
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                listOfUser.clear()
                if(!newText.isNullOrEmpty()) {
                    radius = newText.toDouble()
                }
                viewModel.fetchUsers()
                return true
            }
        })
        return super.onCreateOptionsMenu(menu)
    }
}