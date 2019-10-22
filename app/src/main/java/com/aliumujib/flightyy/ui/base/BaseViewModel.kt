package com.aliumujib.flightyy.ui.base

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import com.aliumujib.flightyy.R
import com.aliumujib.flightyy.presentation.state.Resource
import com.aliumujib.flightyy.presentation.state.Status
import com.aliumujib.flightyy.ui.utils.Event
import com.aliumujib.flightyy.ui.utils.NavigationCommand

abstract class BaseViewModel : ViewModel() {

    // FOR ERROR HANDLER
    protected val _snackbarError = MutableLiveData<Event<Int>>()
    val snackBarError: LiveData<Event<Int>> get() = _snackbarError

    // FOR ERROR HANDLER
    protected val _snackbarStringError = MutableLiveData<Event<String>>()
    val snackBarStringError: LiveData<Event<String>> get() = _snackbarStringError

    // FOR NAVIGATION
    private val _navigation = MutableLiveData<Event<NavigationCommand>>()
    val navigation: LiveData<Event<NavigationCommand>> = _navigation

    // FOR LOADING
    private val _loading = MutableLiveData<Event<Status>>()
    val loading: LiveData<Event<Status>> = _loading

    fun setStatus(status: Status) {
        _loading.value = Event(status)
    }

    fun handleStatus(resource: Resource<*>) {
        setStatus(resource.status)
        if (resource.status == Status.ERROR)
            if (resource.message != null) {
                resource.message.let {
                    _snackbarStringError.value = Event(it)
                }
            } else {
                _snackbarError.value = Event(R.string.an_error_happened)
            }
    }

    fun showSnackBarError(@StringRes resourceId: Int) {
        _snackbarError.value = Event(resourceId)
    }

    fun showSnackBarError(error: String) {
        _snackbarStringError.value = Event(error)
    }

    /**
     * Convenient method to handle navigation from a [ViewModel]
     */
    fun navigate(directions: NavDirections) {
        _navigation.value = Event(NavigationCommand.To(directions))
    }
}