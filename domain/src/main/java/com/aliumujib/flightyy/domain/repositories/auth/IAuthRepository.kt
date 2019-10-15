package com.aliumujib.flightyy.domain.repositories.auth

import io.reactivex.Completable

interface IAuthRepository {

    fun login(clientId: String, clientSecret: String): Completable

}