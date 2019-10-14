package com.aliumujib.flightyy.data.contracts.cache


interface IAuthTokenManager {

    fun saveToken(token: String?)

    fun getToken(): String?

    fun clearToken()
}