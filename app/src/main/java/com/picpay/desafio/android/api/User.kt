package com.picpay.desafio.android.api

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class User(
    @SerializedName("name")     @PrimaryKey                    val name: String,
    @SerializedName("id")       @ColumnInfo(name = "id")       val id: Int,
    @SerializedName("img")      @ColumnInfo(name = "img")      val img: String,
    @SerializedName("username") @ColumnInfo(name = "username") val username: String
) : Parcelable