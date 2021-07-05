package com.picpay.desafio.android.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.picpay.desafio.android.api.User
import com.picpay.desafio.android.repository.PicPayDataBaseImpl
import com.picpay.desafio.android.repository.Result
import com.picpay.desafio.android.repository.PicPayServiceImpl

class UserViewModel(application: Application) : AndroidViewModel(application) {
    private val database = PicPayDataBaseImpl(getApplication<Application>().applicationContext)
    val needShowSnackBar: MutableLiveData<Boolean> by lazy {MutableLiveData(true)}
    var users: LiveData<Result<List<User>?>> = database.getUsersFromDb()
        private set

    fun updateUserList(){
        needShowSnackBar.value = true
        users = PicPayServiceImpl.getUsers(database)
    }
}