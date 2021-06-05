package com.picpay.desafio.android.api

sealed class Result<out T> {
    data class Success<out R>(val value: R?): Result<R?>()
    data class Error(val exception: Exception): Result<Nothing>()
}