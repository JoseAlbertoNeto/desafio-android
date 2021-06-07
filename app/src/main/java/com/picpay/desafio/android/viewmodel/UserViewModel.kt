package com.picpay.desafio.android.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.picpay.desafio.android.api.Result
import com.picpay.desafio.android.repository.PicPayServiceImpl

class UserViewModel: ViewModel() {
    val users: MutableLiveData<LiveData<Result<List<User>?>>> by lazy {
        MutableLiveData(PicPayServiceImpl.getUsers())
    }

    fun updateUserList(){
        users.value = PicPayServiceImpl.getUsers()
    }
}