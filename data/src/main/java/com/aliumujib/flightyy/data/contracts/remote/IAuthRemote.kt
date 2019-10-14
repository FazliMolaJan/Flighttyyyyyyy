package com.aliumujib.flightyy.data.contracts.remote

import io.reactivex.Observable

interface IAuthRemote {

    fun login(clientId: String, clientSecret: String): Observable<String>

}