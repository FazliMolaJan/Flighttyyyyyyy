package com.aliumujib.flightyy.data.remote.api.utils

import com.aliumujib.flightyy.data.remote.api.retrofit.APIServiceFactory
import com.google.gson.Gson
import javax.inject.Inject

class TokenRefresher @Inject constructor(
    var gson: Gson,
    var baseURL: String
) {

    fun refreshToken(clientId: String, clientSecret: String): String? {
        val hashMap = HashMap<String, Any>()
        hashMap["client_id"] = clientId
        hashMap["client_secret"] = clientSecret
        hashMap["grant_type"] = "client_credentials"
        return APIServiceFactory.makeApiService(
            APIServiceFactory.makeSimplerRetrofitInstance(
                baseURL,
                gson
            )
        )
            .nonRxLogin(hashMap).execute().body()?.access_token
    }

}