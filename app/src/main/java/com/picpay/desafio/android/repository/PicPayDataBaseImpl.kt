package com.picpay.desafio.android.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.room.Room
import com.picpay.desafio.android.api.PicPayDatabase
import com.picpay.desafio.android.api.User

class EmptyDataBase(override val message: String): Exception(message)

class PicPayDataBaseImpl(private val context: Context){
    companion object{
        private const val DB_NAME = "user_db"
    }

    private val database by lazy {
        Room.databaseBuilder(
            context,
            PicPayDatabase::class.java,
            DB_NAME
        ).build()
    }

    val userDao by lazy { database.userDao() }

    /**
     * Get users from room database
     * @return livedata with all users in database
     */
    fun getUsersFromDb(): LiveData<Result<List<User>?>> {
        return liveData {
            val response = PicPayDataBaseImpl(context).userDao.getAll()
            if(response.isEmpty()){
                emit(Result.Error(EmptyDataBase("Empty database")))
            } else{
                emit(Result.Success(response))
            }
        }
    }
}