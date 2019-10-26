package com.aliumujib.flightyy.ui.base

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import com.aliumujib.flightyy.ui.utils.NavigationCommand
import com.aliumujib.flightyy.ui.utils.SingleLiveData

abstract class BaseViewModel : ViewModel() {

    // FOR ERROR HANDLER
    private val _error = SingleLiveData<Int>()
    val error: LiveData<Int> get() = _error

    // FOR ERROR HANDLER
    private val _stringError = SingleLiveData<String>()
    val stringError: LiveData<String> get() = _stringError

    // FOR NAVIGATION
    private val _navigation = SingleLiveData<NavigationCommand>()
    val navigation: LiveData<NavigationCommand> = _navigation

    fun showError(@StringRes resourceId: Int) {
        _error.value = resourceId
    }

    fun showError(error: String) {
        _stringError.value = error
    }

    /**
     * Convenient method to handle navigation from a [ViewModel]
     */
    fun navigate(directions: NavDirections) {
        _navigation.value = NavigationCommand.To(directions)
    }

}