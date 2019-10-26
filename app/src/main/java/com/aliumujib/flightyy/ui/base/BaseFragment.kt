package com.aliumujib.flightyy.ui.base

import android.os.Build
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.transition.TransitionInflater
import com.aliumujib.flightyy.ui.utils.NavigationCommand
import com.aliumujib.flightyy.ui.utils.ext.findNavController
import com.aliumujib.flightyy.ui.utils.observe

abstract class BaseFragment : Fragment() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observeNavigation(getViewModel())

        observe(getViewModel().error, ::showError)
        observe(getViewModel().stringError, ::showError)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            sharedElementEnterTransition =
                TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        }
    }

    abstract fun getViewModel(): BaseViewModel

    // UTILS METHODS ---

    open fun showError(string: String?) {

    }

    private fun showError(@StringRes string: Int?) {
        string?.let {
            showError(resources.getString(it))
        }
    }

    /**
     * Observe a [NavigationCommand] [Event] [LiveData].
     * When this [LiveData] is updated, [Fragment] will navigate to its destination
     */
    private fun observeNavigation(viewModel: BaseViewModel) {
        viewModel.navigation.observe(viewLifecycleOwner, Observer {
            it?.let { command ->
                navigate(command)
            }
        })
    }

    fun navigate(navigationCommand: NavigationCommand) {
        when (navigationCommand) {
            is NavigationCommand.To -> findNavController().navigate(
                navigationCommand.directions,
                getExtras()
            )
            is NavigationCommand.Back -> findNavController().navigateUp()
        }
    }

    /**
     * [FragmentNavigatorExtras] mainly used to enable Shared Element transition
     */
    open fun getExtras(): FragmentNavigator.Extras = FragmentNavigatorExtras()
}