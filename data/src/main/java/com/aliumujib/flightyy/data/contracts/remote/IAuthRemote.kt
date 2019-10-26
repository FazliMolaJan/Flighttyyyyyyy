package com.aliumujib.flightyy.data.contracts.remote

import io.reactivex.Single

interface IAuthRemote {

    fun login(clientId: String, clientSecret: String): Single<String>

}