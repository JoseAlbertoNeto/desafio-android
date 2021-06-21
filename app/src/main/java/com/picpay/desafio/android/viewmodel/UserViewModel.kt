package com.picpay.desafio.android.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.picpay.desafio.android.api.User
import com.picpay.desafio.android.repository.Result
import com.picpay.desafio.android.repository.PicPayServiceImpl

class UserViewModel(application: Application) : AndroidViewModel(application) {
    val needShowSnackBar: MutableLiveData<Boolean> by lazy {MutableLiveData(true)}
    var users: LiveData<Result<List<User>?>> = PicPayServiceImpl.getUsersFromDb(getApplication<Application>().applicationContext)
        private set

    fun updateUserList(){
        needShowSnackBar.value = true
        users = PicPayServiceImpl.getUsers(getApplication<Application>().applicationContext)
    }
}