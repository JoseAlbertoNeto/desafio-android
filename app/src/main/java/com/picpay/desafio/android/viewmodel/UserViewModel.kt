package com.picpay.desafio.android.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.picpay.desafio.android.api.Result
import com.picpay.desafio.android.repository.PicPayServiceImpl

class UserViewModel: ViewModel() {
    var users: LiveData<Result<List<User>?>> = PicPayServiceImpl.getUsers()
        private set

    fun updateUserList(){
        users = PicPayServiceImpl.getUsers()
    }
}