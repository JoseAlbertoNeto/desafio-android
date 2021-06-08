package com.picpay.desafio.android.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.room.Room
import com.google.gson.GsonBuilder
import com.picpay.desafio.android.api.PicPayDatabase
import com.picpay.desafio.android.api.PicPayServiceInterface
import com.picpay.desafio.android.api.User
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.UnknownHostException

class FailedToGetDataException(override val message: String, val value: List<User>? = null): Exception(message)
class NoInternetConnectionException(override val message: String, val value: List<User>? = null): Exception(message)

object PicPayServiceImpl {
    private const val URL = "https://609a908e0f5a13001721b74e.mockapi.io/picpay/api/"
    private const val DB_NAME = "user_db"
    private val service: PicPayServiceInterface by lazy {
        Retrofit.Builder()
            .baseUrl(URL)
            .client(OkHttpClient.Builder().build())
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(PicPayServiceInterface::class.java)
    }

    private fun getDB(context: Context): PicPayDatabase {
        return Room.databaseBuilder(
            context,
            PicPayDatabase::class.java,
            DB_NAME
        ).build()
    }

    fun getUsers(context: Context): LiveData<Result<List<User>?>> {
        return liveData{
            try{
                val response = service.getUsers()
                if(response.isSuccessful){
                    val users = response.body()
                    emit(Result.Success(value = users))
                    if(users != null){
                        getDB(context).userDao().insertAll(users)
                    }
                } else {
                    emit(
                        Result.Error(
                            exception = FailedToGetDataException(
                                "Failed to get data",
                                getDB(context).userDao().getAll()
                            )
                        )
                    )
                }
            } catch (exception: UnknownHostException){
                emit(
                    Result.Error(
                        exception = NoInternetConnectionException(
                            "No internet connection",
                            getDB(context).userDao().getAll()
                        )
                    )
                )
            } catch (exception: Exception){
                emit(Result.Error(exception = exception))
            }
        }
    }
}