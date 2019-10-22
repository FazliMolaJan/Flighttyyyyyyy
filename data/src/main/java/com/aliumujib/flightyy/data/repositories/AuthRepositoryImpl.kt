package com.aliumujib.flightyy.data.repositories

import com.aliumujib.flightyy.data.contracts.cache.IAuthTokenManager
import com.aliumujib.flightyy.data.contracts.cache.IUserLoginManager
import com.aliumujib.flightyy.data.contracts.remote.IAuthRemote
import com.aliumujib.flightyy.domain.repositories.auth.IAuthRepository
import io.reactivex.Completable
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val userDataManager: IUserLoginManager,
    private val tokenManager: IAuthTokenManager,
    private val authRemote: IAuthRemote
) : IAuthRepository {

    override fun isUserLoggedIn(): Boolean {
        return userDataManager.getClientId() != null && userDataManager.getClientSecret() != null && tokenManager.getToken() != null
    }


    override fun login(clientId: String, clientSecret: String): Completable {
        return authRemote.login(clientId, clientSecret).doOnNext {
            tokenManager.saveToken(it)
            userDataManager.saveClientId(clientId)
            userDataManager.saveClientSecret(clientSecret)
        }.ignoreElements()
    }

}