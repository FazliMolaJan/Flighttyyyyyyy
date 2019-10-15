package com.aliumujib.flightyy.data.remote.impl

import com.aliumujib.flightyy.data.contracts.remote.IAuthRemote
import com.aliumujib.flightyy.data.remote.api.retrofit.ApiService
import io.reactivex.Observable
import javax.inject.Inject

class AuthRemote @Inject constructor(
   private val apiService: ApiService
) : IAuthRemote {

    override fun login(clientId: String, clientSecret: String): Observable<String> {
        val hashMap = HashMap<String, Any>()
        hashMap["client_id"] = clientId
        hashMap["client_secret"] = clientSecret
        hashMap["grant_type"] = "client_credentials"
        return apiService.login(hashMap).map {
            it.access_token
        }
    }

}