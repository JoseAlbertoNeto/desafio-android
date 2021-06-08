package com.picpay.desafio.android.api

import retrofit2.Response
import retrofit2.http.GET

interface PicPayServiceInterface {
    @GET("users")
    suspend fun getUsers(): Response<List<User>>
}