package com.aliumujib.flightyy.data.remote.api.utils

import android.util.Log
import com.aliumujib.flightyy.data.contracts.cache.IAuthTokenManager
import com.aliumujib.flightyy.data.contracts.cache.IUserLoginManager
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

class AccessTokenAuthenticator @Inject constructor(
    private val tokenManager: IAuthTokenManager,
    private val userLoginManager: IUserLoginManager,
    private val tokenRefresher: TokenRefresher
) : Authenticator {

    private var countOfRetry: Int = 0

    override fun authenticate(route: Route, response: Response): Request? {
        if (response.code() == 401 && countOfRetry < 4) {
            // We need to have a token in order to refresh it.
            Log.d(AccessTokenAuthenticator::class.java.simpleName, "ReAuthenticating token")

            val clientId = userLoginManager.getClientId()
            val clientSecret = userLoginManager.getClientSecret()

            if (clientId != null && clientSecret != null) {
                val newToken = tokenRefresher.refreshToken(clientId, clientSecret)
                tokenManager.saveToken(newToken)
            }

            countOfRetry++

            Log.d(
                AccessTokenAuthenticator::class.java.simpleName,
                "Re authenticating token success on retry $countOfRetry"
            )

            return response.request().newBuilder()
                .header("Authorization", "Bearer " + tokenManager.getToken())
                .build()

        } else {
            countOfRetry = 0
            Log.d(AccessTokenAuthenticator::class.java.simpleName, "No need to refresh token")
            return null
        }
    }


}
