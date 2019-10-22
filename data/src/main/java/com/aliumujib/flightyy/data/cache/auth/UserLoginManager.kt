package com.aliumujib.flightyy.data.cache.auth

import android.content.Context
import com.aliumujib.flightyy.data.cache.CoreSharedPrefManager
import com.aliumujib.flightyy.data.contracts.cache.IAuthTokenManager
import com.aliumujib.flightyy.data.contracts.cache.IUserLoginManager
import javax.inject.Inject

class UserLoginManager @Inject constructor(private val context: Context) : IUserLoginManager,
    CoreSharedPrefManager(context) {

    override fun saveClientSecret(secret: String?) {
        secret?.let {
            savePref(CLIENT_SECRET, it)
        }
    }

    override fun saveClientId(id: String?) {
        id?.let {
            savePref(CLIENT_ID, it)
        }
    }

    override fun getClientId(): String? {
        return getPref(CLIENT_ID)
    }

    override fun getClientSecret(): String? {
        return getPref(CLIENT_SECRET)
    }

    override fun clearData() {
        delete(CLIENT_ID)
        delete(CLIENT_SECRET)
    }

    init {

    }

    companion object {
        const val CLIENT_SECRET = "CLIENT_SECRET"
        const val CLIENT_ID = "CLIENT_ID"
    }

}