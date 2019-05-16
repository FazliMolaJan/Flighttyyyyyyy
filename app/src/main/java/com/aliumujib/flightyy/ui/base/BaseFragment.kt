package com.aliumujib.flightyy.ui.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import com.aliumujib.flightyy.presentation.state.Resource
import com.aliumujib.flightyy.presentation.state.Status
import com.aliumujib.flightyy.ui.utils.ext.findNavController
import com.aliumujib.flightyy.ui.utils.ext.setupSnackbar
import com.aliumujib.flightyy.ui.utils.ext.setupStringSnackbar
import com.google.android.material.snackbar.Snackbar
import com.aliumujib.flightyy.ui.utils.NavigationCommand
import dagger.android.support.AndroidSupportInjection
import timber.log.Timber

abstract class BaseFragment : Fragment() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observeNavigation(getViewModel())
        observeLoading(getViewModel())

        setupSnackbar(this, getViewModel().snackBarError, Snackbar.LENGTH_LONG)
        setupStringSnackbar(this, getViewModel().snackBarStringError, Snackbar.LENGTH_LONG)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidSupportInjection.inject(this)
    }

    abstract fun getViewModel(): BaseViewModel

    // UTILS METHODS ---

    /**
     * Observe a [NavigationCommand] [Event] [LiveData].
     * When this [LiveData] is updated, [Fragment] will navigate to its destination
     */
    private fun observeNavigation(viewModel: BaseViewModel) {
        viewModel.navigation.observe(viewLifecycleOwner, Observer {
            it?.getContentIfNotHandled()?.let { command ->
                navigate(command)
            }
        })
    }


    fun navigate(navigationCommand: NavigationCommand){
        when (navigationCommand) {
            is NavigationCommand.To -> findNavController().navigate(navigationCommand.directions, getExtras())
            is NavigationCommand.Back -> findNavController().navigateUp()
        }
    }


    /**
     * Observe a [Resource.Status] [Event] [LiveData].
     * When this [LiveData] is updated, [Fragment] will display or remove the progress indicator
     */
    private fun observeLoading(viewModel: BaseViewModel) {
        viewModel.loading.observe(viewLifecycleOwner, Observer {
            it?.getContentIfNotHandled()?.let { status ->
                if (status == Status.LOADING) {
                    Timber.v("LOADING")
                    showLoading()
                } else {
                    Timber.v("NOT LOADING")
                    hideLoading()
                }
            }
        })
    }


    open fun showLoading() {

    }

    open fun hideLoading() {

    }


    /**
     * [FragmentNavigatorExtras] mainly used to enable Shared Element transition
     */
    open fun getExtras(): FragmentNavigator.Extras = FragmentNavigatorExtras()
}