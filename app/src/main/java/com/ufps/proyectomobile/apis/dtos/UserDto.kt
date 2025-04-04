package com.ufps.proyectomobile.apis.dtos

import com.google.gson.annotations.SerializedName

data class User(
    val username: String,
    val password: String,
)

data class Token(
    val token: String,
)

data class TokenDto(
    @SerializedName("token") val token: String,
)

fun TokenDto.toModel() = Token(
    token = this.token
)