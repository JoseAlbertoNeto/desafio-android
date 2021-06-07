package com.picpay.desafio.android.view

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.picpay.desafio.android.R
import com.picpay.desafio.android.api.Result
import com.picpay.desafio.android.repository.FailedToGetDataException
import com.picpay.desafio.android.repository.NoInternetConnectionException
import com.picpay.desafio.android.view.recyclerview.UserListAdapter
import com.picpay.desafio.android.viewmodel.UserViewModel

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var adapter: UserListAdapter
    private val userViewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recyclerView = findViewById(R.id.recyclerView)
        progressBar = findViewById(R.id.user_list_progress_bar)
        setupData()
    }

    private fun setupData(){
        progressBar.visibility = View.VISIBLE
        userViewModel.users.observe(this, {
            it.observe(this, { result ->
                when(result){
                    is Result.Success -> {
                        progressBar.visibility = View.GONE
                        recyclerView.layoutManager = LinearLayoutManager(this)
                        adapter = UserListAdapter()
                        adapter.users = result.value!!
                        recyclerView.adapter = adapter
                        recyclerView.visibility = View.VISIBLE
                    }
                    is Result.Error -> {
                        progressBar.visibility = View.GONE
                        recyclerView.visibility = View.GONE

                        val message = when(result.exception){
                            is NoInternetConnectionException -> getString(R.string.no_internet)
                            is FailedToGetDataException -> getString(R.string.failed_get_data)
                            else -> getString(R.string.error)
                        }
                        Snackbar.make(findViewById(R.id.recyclerView), message, Snackbar.LENGTH_SHORT)
                            .setAction(R.string.retry){userViewModel.updateUserList()}
                            .show()
                    }
                }
            })
        })
    }
}
