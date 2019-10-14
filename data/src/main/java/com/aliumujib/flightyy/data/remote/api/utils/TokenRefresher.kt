package com.aliumujib.flightyy.data.remote.api.utils

import com.aliumujib.flightyy.BuildConfig
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
        return APIServiceFactory.makeApiService(
            APIServiceFactory.makeSimplerRetrofitInstance(
                baseURL,
                gson
            )
        )
            .login(hashMap).blockingFirst().access_token
    }

}