package com.picpay.desafio.android.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.picpay.desafio.android.R
import com.picpay.desafio.android.databinding.ActivityMainBinding
import com.picpay.desafio.android.repository.Result
import com.picpay.desafio.android.repository.FailedToGetDataException
import com.picpay.desafio.android.repository.NoInternetConnectionException
import com.picpay.desafio.android.view.recyclerview.UserListAdapter
import com.picpay.desafio.android.api.User
import com.picpay.desafio.android.viewmodel.UserViewModel

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private lateinit var binding: ActivityMainBinding
    private val adapter = UserListAdapter()
    private val userViewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.toolbar.title = ""

        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        setupContactList()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                updateUserList()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupContactList(){
        binding.progressBar.visibility = View.VISIBLE
        userViewModel.users.observe(this, {result ->
            binding.progressBar.visibility = View.GONE
            when(result){
                is Result.Success -> {
                    setupRecyclerView(result.value!!)
                    showSnackBar(getString(R.string.contacts_updated), R.color.colorAccent)
                }
                is Result.Error -> {
                    val (message, userDbList) = when(result.exception){
                        is NoInternetConnectionException -> Pair(getString(R.string.no_internet), result.exception.value)
                        is FailedToGetDataException -> Pair(getString(R.string.failed_get_data), result.exception.value)
                        else -> Pair(getString(R.string.error), null)
                    }
                    if(userDbList != null)
                        setupRecyclerView(userDbList)
                    showSnackBar(message, R.color.colorError)
                }
            }
        })
    }

    private fun setupRecyclerView(result: List<User>){
        if(binding.recyclerView.layoutManager == null){
            binding.recyclerView.layoutManager = LinearLayoutManager(this)
        }
        adapter.users = result
        binding.recyclerView.adapter = adapter
    }

    private fun showSnackBar(message:String, colorId: Int){
        if(userViewModel.needShowSnackBar.value == true) {
            userViewModel.needShowSnackBar.value = false
            Snackbar.make(binding.recyclerView, message, Snackbar.LENGTH_LONG)
                .setTextColor(getColor(colorId))
                .setBackgroundTint(getColor(R.color.colorBackground))
                .show()
        }
    }

    private fun updateUserList(){
        userViewModel.updateUserList()
        setupContactList()
    }
}
