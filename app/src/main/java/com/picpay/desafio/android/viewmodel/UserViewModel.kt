package com.picpay.desafio.android.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.picpay.desafio.android.api.Result
import com.picpay.desafio.android.repository.PicPayServiceImpl

class UserViewModel: ViewModel() {
    private var users: LiveData<Result<List<User>?>> = getUserList()

    fun getUsers() = users

    fun getUserList(): LiveData<Result<List<User>?>>{
        users = PicPayServiceImpl.getUsers()
        return users
    }
}