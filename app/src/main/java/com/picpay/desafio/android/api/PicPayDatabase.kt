package com.picpay.desafio.android.api

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [User::class], version = 1)
abstract class PicPayDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}