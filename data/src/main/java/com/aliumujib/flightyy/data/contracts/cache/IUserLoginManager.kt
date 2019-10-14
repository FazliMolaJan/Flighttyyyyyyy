package com.aliumujib.flightyy.data.contracts.cache


interface IUserLoginManager {

    fun saveClientSecret(secret: String?)

    fun saveClientId(id: String?)

    fun getClientId(): String?

    fun getClientSecret(): String?

    fun clearData()
}