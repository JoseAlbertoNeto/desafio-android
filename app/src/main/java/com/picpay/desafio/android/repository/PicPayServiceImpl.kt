package com.picpay.desafio.android.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.google.gson.GsonBuilder
import com.picpay.desafio.android.api.PicPayServiceInterface
import com.picpay.desafio.android.api.User
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.UnknownHostException

class FailedToGetDataException(override val message: String): Exception(message)
class NoInternetConnectionException(override val message: String): Exception(message)

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

    /**
     * Get users from webservice and then add/update users in room database
     * @param database room database
     * @return livedata with all users in webservice
     */
    fun getUsers(database: PicPayDataBaseImpl): LiveData<Result<List<User>?>> {
        return liveData{
            try{
                val response = service.getUsers()
                if(response.isSuccessful){
                    val users = response.body()
                    emit(Result.Success(value = users))
                    if(users != null){
                        val usersInDb= database.userDao.getAll().toSet()
                        val usersToAdd = users.minus(usersInDb)
                        if(usersToAdd.isNotEmpty())
                            database.userDao.insertAll(usersToAdd)
                    }
                } else {
                    emit(Result.Error(FailedToGetDataException("Failed to get data")))
                }
            } catch (exception: UnknownHostException){
                emit(Result.Error(NoInternetConnectionException("No internet connection")))
            } catch (exception: Exception){
                emit(Result.Error(exception = exception))
            }
        }
    }
}