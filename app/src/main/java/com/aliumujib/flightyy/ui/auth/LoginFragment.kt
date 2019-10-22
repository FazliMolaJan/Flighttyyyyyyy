package com.aliumujib.flightyy.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.aliumujib.flightyy.R
import com.aliumujib.flightyy.presentation.state.Resource
import com.aliumujib.flightyy.presentation.state.Status
import com.aliumujib.flightyy.presentation.viewmodels.LoginViewModel
import com.aliumujib.flightyy.ui.MainActivity
import com.aliumujib.flightyy.ui.base.BaseFragment
import com.aliumujib.flightyy.ui.base.BaseViewModel
import com.aliumujib.flightyy.ui.inject.ViewModelFactory
import com.aliumujib.flightyy.ui.utils.ext.hideLoadingDialog
import com.aliumujib.flightyy.ui.utils.ext.showLoadingDialog
import com.aliumujib.flightyy.ui.utils.observe
import kotlinx.android.synthetic.main.fragment_auth.*
import javax.inject.Inject

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class LoginFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: LoginViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(LoginViewModel::class.java)
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_auth, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observe(viewModel.loginStatus, ::handleLoginStatus)

        login_button.setOnClickListener {
            viewModel.login(client_id.getText(), client_secret.getText())
        }
    }

    private fun handleLoginStatus(resource: Resource<Unit>?) {
        resource?.status?.let {
            when (it) {
                Status.SUCCESS -> {
                    hideLoadingDialog()
                    activity?.let { parent ->
                        MainActivity.start(parent)
                    }
                }
                Status.LOADING -> {
                    showLoadingDialog()
                }
                else -> {
                    hideLoadingDialog()
                }
            }
        }
    }
}
