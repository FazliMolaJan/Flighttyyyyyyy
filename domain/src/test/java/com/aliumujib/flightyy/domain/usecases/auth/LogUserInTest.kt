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
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LogUserInTest {

    private lateinit var logUserIn: LogUserIn
    @Mock
    lateinit var authRepository: IAuthRepository
    @Mock
    lateinit var postExecutionThread: PostExecutionThread

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        logUserIn = LogUserIn(authRepository, postExecutionThread)
    }

    @Test
    fun `confirm that calling login with non null credentials completes`() {
        stubLogin(Completable.complete())
        val testObserver =
            logUserIn.buildUseCaseCompletable(LogUserIn.Params.make("AAA", "BBB")).test()
        testObserver.assertComplete()
    }

    @Test(expected = IllegalStateException::class)
    fun `confirm that using LogUserIn with null params throws an exception`() {
        stubLogin(Completable.complete())
        val testObserver = logUserIn.buildUseCaseCompletable().test()
        testObserver.assertComplete()
    }


    private fun stubLogin(completable: Completable) {
        whenever(authRepository.login(any(), any()))
            .thenReturn(completable)
    }

}