package com.aliumujib.flightyy.presentation.viewmodels

import androidx.lifecycle.LiveData
import com.aliumujib.flightyy.R
import com.aliumujib.flightyy.presentation.models.AirportModel
import com.aliumujib.flightyy.ui.base.BaseViewModel
import com.aliumujib.flightyy.ui.filter.FiltersFragmentDirections
import com.aliumujib.flightyy.ui.utils.mutableLiveDataOf
import javax.inject.Inject

class FlightFiltersViewModel @Inject constructor() : BaseViewModel() {

    private var selectedOriginAirport: AirportModel? = null
    private var selectedDestinationAirport: AirportModel? = null
    private var selectedDate: String? = null

    private var _originLiveData = mutableLiveDataOf<AirportModel>()
    private var _destinationLiveData = mutableLiveDataOf<AirportModel>()
    private var _selectedDateLiveData = mutableLiveDataOf<String>()

    val origin: LiveData<AirportModel> = _originLiveData
    val destination: LiveData<AirportModel> = _destinationLiveData
    val date: LiveData<String> = _selectedDateLiveData

    fun setOriginAndDestination(origin: AirportModel, destination: AirportModel) {
        selectedOriginAirport = origin
        selectedDestinationAirport = destination
        _originLiveData.value = origin
        _destinationLiveData.value = destination
    }

    fun setSelectedDate(date: String) {
        selectedDate = date
        _selectedDateLiveData.value = date
    }

    fun verifyData() {
        when {
            selectedOriginAirport == null -> {
                showError(R.string.please_select_your_origin_airport)
                return
            }
            selectedDestinationAirport == null -> {
                showError(R.string.please_select_your_dest_airport)
                return
            }
            selectedDate == null -> {
                showError(R.string.please_select_your_date)
                return
            }
            selectedOriginAirport!!.name == selectedDestinationAirport!!.name -> {
                showError(R.string.please_select_two_airports)
                return
            }
            else -> navigate(
                FiltersFragmentDirections.actionFiltersFragmentToScheduleListFragment(
                    selectedOriginAirport!!,
                    selectedDestinationAirport!!,
                    selectedDate!!
                )
            )
        }
    }
}