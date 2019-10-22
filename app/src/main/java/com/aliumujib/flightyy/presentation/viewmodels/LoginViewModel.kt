package com.aliumujib.flightyy.presentation.viewmodels


import androidx.lifecycle.LiveData
import com.aliumujib.flightyy.R
import com.aliumujib.flightyy.domain.usecases.auth.CheckIfUserIsLoggedIn
import com.aliumujib.flightyy.domain.usecases.auth.LogUserIn
import com.aliumujib.flightyy.presentation.state.Resource
import com.aliumujib.flightyy.ui.base.BaseViewModel
import com.aliumujib.flightyy.ui.utils.SingleLiveData
import io.reactivex.observers.DisposableCompletableObserver
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val logUserIn: LogUserIn,
    private val userIsLoggedIn: CheckIfUserIsLoggedIn
) : BaseViewModel() {

    private val _loginStatus: SingleLiveData<Resource<Unit>> = SingleLiveData()
    val loginStatus: LiveData<Resource<Unit>> = _loginStatus

    init {
        checkLoginStatus()
    }

    fun checkLoginStatus() {
        if (userIsLoggedIn.execute()) {
            _loginStatus.postValue(Resource.success(Unit))
        }
    }

    fun login(clientId: String, clientSecret: String) {
        if (clientId.isEmpty()) {
            showSnackBarError(R.string.please_input_client_id)
            _loginStatus.postValue(Resource.error(null))
            return
        } else if (clientSecret.isEmpty()) {
            _loginStatus.postValue(Resource.error(null))
            showSnackBarError(R.string.please_input_client_secret)
            return
        }

        _loginStatus.postValue(Resource.loading())
        logUserIn.execute(LoginObserver(), LogUserIn.Params.make(clientId, clientSecret))
    }


    inner class LoginObserver : DisposableCompletableObserver() {

        override fun onError(e: Throwable) {
            e.printStackTrace()
            showSnackBarError(R.string.error_occured)
            _loginStatus.postValue(
                Resource.error(e.message)
            )
        }

        override fun onComplete() {
            _loginStatus.postValue(
                Resource.success(Unit)
            )
        }

    }


}