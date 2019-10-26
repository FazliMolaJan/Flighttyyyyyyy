package com.aliumujib.flightyy.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.aliumujib.flightyy.R
import com.aliumujib.flightyy.presentation.models.AirportModel
import com.aliumujib.flightyy.presentation.models.AirportSearchResults
import com.aliumujib.flightyy.presentation.state.Resource
import com.aliumujib.flightyy.presentation.state.Status
import com.aliumujib.flightyy.presentation.viewmodels.SearchAirportsViewModel
import com.aliumujib.flightyy.ui.MainActivity
import com.aliumujib.flightyy.ui.adapters.base.BindableItemClickListener
import com.aliumujib.flightyy.ui.adapters.base.SingleLayoutAdapter
import com.aliumujib.flightyy.ui.base.BaseFragment
import com.aliumujib.flightyy.ui.base.BaseViewModel
import com.aliumujib.flightyy.ui.inject.ViewModelFactory
import com.aliumujib.flightyy.ui.utils.ext.showSnackbar
import com.aliumujib.flightyy.ui.utils.observe
import com.google.android.material.snackbar.Snackbar
import com.jakewharton.rxbinding2.widget.RxTextView
import dagger.android.support.AndroidSupportInjection
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_airport_search.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class AirportSearchFragment : BaseFragment(), BindableItemClickListener<AirportModel> {


    override fun getViewModel(): BaseViewModel {
        return searchViewModel
    }

    override fun onItemClicked(data: AirportModel) {
        airportsAdapter.clearItems()
        searchViewModel.setAirportForCurrentMode(data)
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var airportsAdapter: SingleLayoutAdapter<AirportModel>

    private lateinit var searchViewModel: SearchAirportsViewModel

    val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidSupportInjection.inject(this)

        searchViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(SearchAirportsViewModel::class.java)

        searchViewModel.fetchAirports()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_airport_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        origin_et.onFocusChangeListener = View.OnFocusChangeListener { _, p1 ->
            origin_indicator.isChecked = p1
        }

        dest_et.onFocusChangeListener = View.OnFocusChangeListener { _, p1 ->
            if (p1) {
                airportsAdapter.clearItems()
            }
            dest_indicator.isChecked = p1
        }

        airports_rv.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = airportsAdapter
        }

        compositeDisposable.add(RxTextView.afterTextChangeEvents(origin_et.getEditText())
            .filter {
                it.view().text.toString().length > 3
            }
            .debounce(500, TimeUnit.MILLISECONDS)
            .map {
                it.view().text.toString()
            }
            .subscribe {
                searchViewModel.searchOrigin(it)
            })

        compositeDisposable.add(RxTextView.afterTextChangeEvents(dest_et.getEditText())
            .filter {
                it.view().text.toString().length > 3
            }
            .debounce(500, TimeUnit.MILLISECONDS)
            .map {
                it.view().text.toString()
            }
            .subscribe {
                searchViewModel.searchDestination(it)
            })

        observe(searchViewModel.airportsData, ::handleSearchData)
        observeAirports()
        observe(searchViewModel.results, ::sendDataBack)
    }

    private fun observeAirports() {
        searchViewModel.origin.observe(viewLifecycleOwner, Observer {
            origin_et.setText(it?.name)
        })

        searchViewModel.destination.observe(viewLifecycleOwner, Observer {
            dest_et.setText(it?.name)
        })
    }

    private fun sendDataBack(it: AirportSearchResults?) {
        var bundle = Bundle()
        bundle.apply {
            putParcelable("results", it)
        }
        (activity as MainActivity).navigateBackWithResult(bundle)
    }

    private fun handleSearchData(it: Resource<List<AirportModel>>?) {
        it?.let {
            when {
                it.status == Status.SUCCESS -> it.data?.let { airports ->
                    airportsAdapter.setData(airports)
                }
                it.status == Status.ERROR -> {
                    it.message?.let { message ->
                        showSnackbar(message, Snackbar.LENGTH_LONG)
                    }
                }
                else -> {
                    // Handle loading
                }
            }
        }
    }


    override fun showError(string: String?) {
        string?.let {
            showSnackbar(string, Snackbar.LENGTH_LONG)
        }
    }

    private val onFocusChangedListener = View.OnFocusChangeListener { p0, p1 ->
        if (p1 and (p0?.id == R.id.origin_et)) {
            origin_indicator.isChecked = true
            dest_indicator.isChecked = false
        } else if (p1 and (p0?.id == R.id.dest_et)) {
            origin_indicator.isChecked = false
            dest_indicator.isChecked = true
        }
    }
}
