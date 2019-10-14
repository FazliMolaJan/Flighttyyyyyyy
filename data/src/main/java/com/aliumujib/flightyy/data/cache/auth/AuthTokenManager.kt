package com.aliumujib.flightyy.data.cache.auth

import android.content.Context
import com.aliumujib.flightyy.data.cache.CoreSharedPrefManager
import com.aliumujib.flightyy.data.contracts.cache.IAuthTokenManager
import javax.inject.Inject

class AuthTokenManager @Inject constructor(var context: Context) : IAuthTokenManager,
    CoreSharedPrefManager(context) {

    init {

    }

    override fun saveToken(token: String?) {
        token?.let {
            savePref(TOKEN, token)
        }
    }

    override fun getToken(): String? {
        return if (getPref<String>(TOKEN, null) != null) {
            getPref(TOKEN)
        } else {
            null
        }
    }

    override fun clearToken() {
        delete(TOKEN)
    }

    companion object {
        const val TOKEN = "auth_token"
    }

}