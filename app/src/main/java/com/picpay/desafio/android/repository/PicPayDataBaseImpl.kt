package com.picpay.desafio.android.repository

import android.content.Context
import androidx.room.Room
import com.picpay.desafio.android.api.PicPayDatabase

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
}