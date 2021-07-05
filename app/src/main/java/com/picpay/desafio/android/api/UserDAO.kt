package com.picpay.desafio.android.api

import androidx.room.*

@Dao
interface UserDao {
    @Query("SELECT * FROM user WHERE name LIKE :first LIMIT 1")
    suspend fun findByName(first: String): User

    @Query("SELECT * FROM user WHERE username LIKE :first LIMIT 1")
    suspend fun findByUsername(first: String): User

    @Query("SELECT * FROM user")
    suspend fun getAll(): List<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users: List<User>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users: Set<User>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg users: User)

    @Delete
    suspend fun delete(user: User)
}