package com.aliumujib.flightyy.presentation.viewmodels


import androidx.lifecycle.LiveData
import com.aliumujib.flightyy.R
import com.aliumujib.flightyy.domain.usecases.auth.LogUserIn
import com.aliumujib.flightyy.presentation.state.Resource
import com.aliumujib.flightyy.presentation.state.Status
import com.aliumujib.flightyy.ui.base.BaseViewModel
import com.aliumujib.flightyy.ui.utils.SingleLiveData
import io.reactivex.observers.DisposableCompletableObserver
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val logUserIn: LogUserIn
) : BaseViewModel() {

    private val _loginStatus: SingleLiveData<Resource<Unit>> = SingleLiveData()
    val loginStatus: LiveData<Resource<Unit>> = _loginStatus


    fun login(clientId: String, clientSecret: String) {
        if (clientId.isEmpty()) {
            showSnackBarError(R.string.please_input_client_id)
            return
        } else if (clientSecret.isEmpty()) {
            showSnackBarError(R.string.please_input_client_secret)
            return
        }
        _loginStatus.postValue(Resource.loading())
        logUserIn.execute(LoginObserver(), LogUserIn.Params.make(clientId, clientSecret))
    }


    inner class LoginObserver : DisposableCompletableObserver() {

        override fun onError(e: Throwable) {
            e.printStackTrace()
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