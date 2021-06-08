package com.picpay.desafio.android.repository

sealed class Result<out T> {
    data class Success<out R>(val value: R?): Result<R?>()
    data class Error(val exception: Exception): Result<Nothing>()
}