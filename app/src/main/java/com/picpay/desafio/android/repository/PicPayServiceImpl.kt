package com.picpay.desafio.android.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.google.gson.GsonBuilder
import com.picpay.desafio.android.api.PicPayServiceInterface
import com.picpay.desafio.android.api.Result
import com.picpay.desafio.android.viewmodel.User
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object PicPayServiceImpl {
    private const val URL = "https://609a908e0f5a13001721b74e.mockapi.io/picpay/api/"
    private val service: PicPayServiceInterface by lazy {
        Retrofit.Builder()
            .baseUrl(URL)
            .client(OkHttpClient.Builder().build())
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(PicPayServiceInterface::class.java)
    }

    fun getUsers(): LiveData<Result<List<User>?>>{
        return liveData{
            try{
                val response = service.getUsers()
                if(response.isSuccessful){
                    emit(Result.Success(value = response.body()))
                } else {
                    emit(Result.Error(exception = Exception("Failed to get data")))
                }
            } catch (exception: Exception){
                emit(Result.Error(exception = exception))
            }
        }
    }
}