package com.aliumujib.flightyy.data.repositories

import com.aliumujib.flightyy.data.contracts.cache.IAuthTokenManager
import com.aliumujib.flightyy.data.contracts.cache.IUserLoginManager
import com.aliumujib.flightyy.data.contracts.remote.IAuthRemote
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import java.util.*
import kotlin.test.assertEquals

class AuthRepositoryImplTest {

    lateinit var authRepositoryImpl: AuthRepositoryImpl

    @Mock
    lateinit var authRemote: IAuthRemote

    @Mock
    lateinit var authTokenManager: IAuthTokenManager

    @Mock
    lateinit var userLoginManager: IUserLoginManager


    @Before
    fun setup() {

        MockitoAnnotations.initMocks(this)

        authRepositoryImpl =
            AuthRepositoryImpl(
                userLoginManager,
                authTokenManager,
                authRemote
            )
    }


    @Test
    fun `check that isUserLoggedIn only returns true when user has token and login details`() {
        stubStoredUserData()
        assertEquals(authRepositoryImpl.isUserLoggedIn(), true)
    }

    @Test
    fun `check that isUserLoggedIn returns false when user does not have token and login details`() {
        stubEmptyUserData()
        assertEquals(authRepositoryImpl.isUserLoggedIn(), false)
    }

    @Test
    fun `check that calling repository login() method saves values to caches or managers after successfully login`() {
        val clientId = UUID.randomUUID().toString()
        val clientSecret = UUID.randomUUID().toString()
        val token = UUID.randomUUID().toString()
        stubLoginWithAuthRemote(clientId, clientSecret, token)
        val testObserver = authRepositoryImpl.login(clientId, clientSecret).test()
        verify(authTokenManager).saveToken(token)
        verify(userLoginManager).saveClientId(clientId)
        verify(userLoginManager).saveClientSecret(clientSecret)
    }

    private fun stubStoredUserData() {
        whenever(authTokenManager.getToken()).thenReturn(UUID.randomUUID().toString())
        whenever(userLoginManager.getClientId()).thenReturn(UUID.randomUUID().toString())
        whenever(userLoginManager.getClientSecret()).thenReturn(UUID.randomUUID().toString())
    }


    private fun stubEmptyUserData() {
        whenever(authTokenManager.getToken()).thenReturn(null)
        whenever(userLoginManager.getClientId()).thenReturn(null)
        whenever(userLoginManager.getClientSecret()).thenReturn(null)
    }

    private fun stubLoginWithAuthRemote(clientId: String, clientSecret: String, token: String) {
        whenever(
            authRemote.login(
                clientId,
                clientSecret
            )
        ).thenReturn(Observable.just(token))
    }


}