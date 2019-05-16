package com.aliumujib.flightyy.ui.filter


import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.ActionOnlyNavDirections
import com.aliumujib.flightyy.R
import com.aliumujib.flightyy.presentation.models.AirportSearchResults
import com.aliumujib.flightyy.presentation.viewmodels.FlightFiltersViewModel
import com.aliumujib.flightyy.ui.base.BaseFragment
import com.aliumujib.flightyy.ui.base.BaseViewModel
import com.aliumujib.flightyy.ui.inject.ViewModelFactory
import com.aliumujib.flightyy.ui.utils.NavigationCommand
import com.aliumujib.flightyy.ui.utils.NavigationResult
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_filters.*
import java.util.*
import javax.inject.Inject


class FiltersFragment : BaseFragment(), NavigationResult {


    override fun getViewModel(): BaseViewModel {
        return flightFiltersViewModel
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var flightFiltersViewModel: FlightFiltersViewModel

    override fun onNavigationResult(result: Bundle) {
        val resultBundle = result.getParcelable<AirportSearchResults>("results")
        flightFiltersViewModel.setOriginAndDestination(resultBundle.origin, resultBundle.destination)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidSupportInjection.inject(this)

        flightFiltersViewModel = ViewModelProviders.of(this, viewModelFactory).get(FlightFiltersViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_filters, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        origin_et.getEditText().setOnClickListener {
            navigate(NavigationCommand.To(ActionOnlyNavDirections(R.id.action_filtersFragment_to_airportSearchFragment)))
        }

        dest_et.getEditText().setOnClickListener {
            navigate(NavigationCommand.To(ActionOnlyNavDirections(R.id.action_filtersFragment_to_airportSearchFragment)))
        }

        departure_date_et.getEditText().setOnClickListener {
            showTimePicker { date, string ->
                departure_date_et.setText(string)
                flightFiltersViewModel.setSelectedDate(date)
            }
        }

        arrival_date_et.getEditText().setOnClickListener {
            showTimePicker { date, string ->
                arrival_date_et.setText(string)
                flightFiltersViewModel.setSelectedDate(date)
            }
        }

        search_button.setOnClickListener {
            flightFiltersViewModel.verifyData()
            //navigate(NavigationCommand.To(ActionOnlyNavDirections(R.id.action_filtersFragment_to_airportSearchFragment)))
        }

        flightFiltersViewModel.destination.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            dest_et.setText(it.name)
        })

        flightFiltersViewModel.origin.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            origin_et.setText(it.name)
        })
    }


    private fun showTimePicker(callback: (date: Date, string: String) -> Unit) {
        // Use the current date as the default date in the picker
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        // Create a new instance of DatePickerDialog and return it
        val dialog = DatePickerDialog(
            activity,
            DatePickerDialog.OnDateSetListener { view, calendar_year, monthOfYear, dayOfMonth ->

                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, year)
                calendar.set(Calendar.DAY_OF_MONTH, year)

                callback.invoke(calendar.time, "$dayOfMonth/$monthOfYear/$calendar_year")
            },

            year, month, day
        )

        dialog.show()
    }

}
