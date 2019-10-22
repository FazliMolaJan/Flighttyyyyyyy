package com.aliumujib.flightyy.domain.usecases.auth

import com.aliumujib.flightyy.domain.executor.PostExecutionThread
import com.aliumujib.flightyy.domain.models.Airport
import com.aliumujib.flightyy.domain.repositories.airports.IAirportsRepository
import com.aliumujib.flightyy.domain.repositories.auth.IAuthRepository
import com.aliumujib.flightyy.domain.test.DummyDataFactory
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Completable
import io.reactivex.Observable
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CheckIfUserIsLoggedInTest {

    private lateinit var checkIfUserIsLoggedIn: CheckIfUserIsLoggedIn
    @Mock
    lateinit var authRepository: IAuthRepository

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        checkIfUserIsLoggedIn = CheckIfUserIsLoggedIn(authRepository)
    }

    @Test
    fun `confirm that user is LoggedIn in Usecase if user is loggedIn in AuthRepository`() {
        stubLoginStatusCheck(true)
        val isLoggedIn = checkIfUserIsLoggedIn.execute()
        assertEquals(isLoggedIn, authRepository.isUserLoggedIn())
    }

    @Test
    fun `confirm that user is not LoggedIn in Usecase if user is not loggedIn in AuthRepository`() {
        stubLoginStatusCheck(false)
        val isLoggedIn = checkIfUserIsLoggedIn.execute()
        assertEquals(isLoggedIn, authRepository.isUserLoggedIn())
    }


    private fun stubLoginStatusCheck(status: Boolean) {
        whenever(authRepository.isUserLoggedIn())
            .thenReturn(status)
    }

}