package com.aliumujib.flightyy.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.aliumujib.flightyy.domain.usecases.auth.CheckIfUserIsLoggedIn
import com.aliumujib.flightyy.domain.usecases.auth.LogUserIn
import com.aliumujib.flightyy.presentation.state.Status
import com.aliumujib.flightyy.presentation.viewmodels.LoginViewModel
import com.nhaarman.mockito_kotlin.*
import io.reactivex.Completable
import io.reactivex.observers.DisposableCompletableObserver
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import java.util.*

@RunWith(JUnit4::class)
class LoginViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var logUserIn: LogUserIn

    @Mock
    lateinit var userIsLoggedIn: CheckIfUserIsLoggedIn

    private lateinit var loginViewModel: LoginViewModel

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        whenever(logUserIn.buildUseCaseCompletable(any())).thenReturn(Completable.complete())

        loginViewModel =
            LoginViewModel(logUserIn, userIsLoggedIn)
    }

    @Test
    fun `check that calling login executes LogUserIn UseCase`() {
        val id = UUID.randomUUID().toString()
        val secret = UUID.randomUUID().toString()
        loginViewModel.login(id, secret)
        verify(logUserIn, times(1)).execute(any(), eq(LogUserIn.Params.make(id, secret)))
    }

    @Test
    fun `check that CheckIfUserIsLoggedIn UseCase is executed once when viewmodel is created`() {
        verify(userIsLoggedIn, times(1)).execute()
    }

    @Test
    fun `check that calling login with empty client id makes viewmodel return error`() {
        loginViewModel.login("", "AA")

        assertEquals(Status.ERROR, loginViewModel.loginStatus.value?.status)
    }

    @Test
    fun `check that calling login with empty client secret makes viewmodel return error`() {
        loginViewModel.login("BB", "")

        assertEquals(Status.ERROR, loginViewModel.loginStatus.value?.status)
    }

    @Test
    fun `check that calling login with correct parameters successfully emits a success event`() {
        val id = UUID.randomUUID().toString()
        val secret = UUID.randomUUID().toString()
        loginViewModel.login(id, secret)

        argumentCaptor<DisposableCompletableObserver>().apply {
            verify(logUserIn).execute(capture(), eq(LogUserIn.Params.make(id, secret)))
            firstValue.onComplete()
        }

        assertEquals(Status.SUCCESS, loginViewModel.loginStatus.value?.status)
    }

    @Test
    fun `check that calling login with incorrect parameters successfully emits an error event`() {
        val id = UUID.randomUUID().toString()
        val secret = UUID.randomUUID().toString()
        loginViewModel.login(id, secret)

        argumentCaptor<DisposableCompletableObserver>().apply {
            verify(logUserIn).execute(capture(), eq(LogUserIn.Params.make(id, secret)))
            firstValue.onError(IllegalAccessException())
        }

        assertEquals(Status.ERROR, loginViewModel.loginStatus.value?.status)
    }

    @Test
    fun `check that user is successfully logged into the app if CheckIfUserIsLoggedIn executes as true`() {
        whenever(userIsLoggedIn.execute()).thenReturn(true)
        loginViewModel.checkLoginStatus()
        assertEquals(Status.SUCCESS, loginViewModel.loginStatus.value?.status)
    }

    @Test
    fun `check that user is not successfully logged into the app if CheckIfUserIsLoggedIn executes as false`() {
        whenever(userIsLoggedIn.execute()).thenReturn(false)
        loginViewModel.checkLoginStatus()
        assertEquals(null, loginViewModel.loginStatus.value?.status)
    }
}
