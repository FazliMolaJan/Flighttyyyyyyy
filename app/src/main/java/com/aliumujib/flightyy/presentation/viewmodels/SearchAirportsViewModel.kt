package com.aliumujib.flightyy.presentation.viewmodels


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.aliumujib.flightyy.domain.usecases.airports.FetchAirports
import com.aliumujib.flightyy.domain.models.Airport
import com.aliumujib.flightyy.presentation.mappers.AirportMapper
import com.aliumujib.flightyy.presentation.models.AirportSearchResults
import com.aliumujib.flightyy.presentation.models.AirportModel
import com.aliumujib.flightyy.presentation.state.Resource
import com.aliumujib.flightyy.presentation.state.Status
import com.aliumujib.flightyy.ui.utils.SingleLiveData
import com.aliumujib.flightyy.ui.utils.mutableLiveDataOf
import io.reactivex.observers.DisposableObserver
import javax.inject.Inject

class SearchAirportsViewModel @Inject constructor(
    private val fetchAirports: FetchAirports,
    private val modelMapper: AirportMapper
) : ViewModel() {

    private val _airportsData: SingleLiveData<Resource<List<AirportModel>>> = SingleLiveData()
    val airportsData: LiveData<Resource<List<AirportModel>>> = _airportsData

    private val _airportList = mutableListOf<AirportModel>()
    val airportList: List<AirportModel> = _airportList

    private var selectedOriginAirport: AirportModel? = null
    private var selectedDestinationAirport: AirportModel? = null


    private var _originLiveData = mutableLiveDataOf<AirportModel>()
    private var _destinationLiveData = mutableLiveDataOf<AirportModel>()

    val origin: LiveData<AirportModel?> = _originLiveData
    val destination: LiveData<AirportModel?> = _destinationLiveData

    private val _results: SingleLiveData<AirportSearchResults> = SingleLiveData()
    val results: LiveData<AirportSearchResults> = _results

    private enum class SEARCH_MODE(val value: Int) {
        ORIGIN(1), DEST(2), NONE(-3)
    }

    private var searchMode = SEARCH_MODE.NONE


    override fun onCleared() {
        fetchAirports.dispose()
        super.onCleared()
    }


    fun airports(): List<AirportModel> {
        return _airportList
    }

    fun searchOrigin(query: String) {
        selectedDestinationAirport = null
        _destinationLiveData.postValue(null)

        searchMode = SEARCH_MODE.ORIGIN
        searchAirports(query)
    }


    fun searchDestination(query: String) {
        searchMode = SEARCH_MODE.DEST
        searchAirports(query)
    }

    private fun searchAirports(query: String) {
        if (query.length < 3) {
            _airportsData.postValue(
                Resource(
                    Status.ERROR,
                    null,
                    "Your query has to have at least 3 letters."
                )
            )
            return
        }

        val results = _airportList.filter { airport ->
            return@filter airport.city.contains(query, true) ||
                    airport.name.contains(query, true) ||
                    airport.state.contains(query, true)
        }

        if(results.isEmpty()){
            _airportsData.postValue(
                Resource(
                    Status.ERROR,
                    null, "No airports found"
                )
            )
        }else{
            _airportsData.postValue(
                Resource(
                    Status.SUCCESS,
                    results, null
                )
            )
        }
    }

    fun setAirportForCurrentMode(data: AirportModel) {
        if (searchMode == SEARCH_MODE.ORIGIN) {
            selectedOriginAirport = data
            _originLiveData.value = data
        } else if (searchMode == SEARCH_MODE.DEST) {
            selectedDestinationAirport = data
            _destinationLiveData.value = data
        }

        if ((selectedDestinationAirport != null) and (selectedOriginAirport != null)) {
            _results.value =
                AirportSearchResults(selectedOriginAirport!!, selectedDestinationAirport!!)
        }
    }

    init {

    }

    fun fetchAirports() {
        fetchAirports.execute(AirportListSubscriber())
    }

    inner class AirportListSubscriber : DisposableObserver<List<Airport>>() {
        override fun onNext(t: List<Airport>) {
            _airportList.addAll(t.map { modelMapper.mapToView(it) })
        }

        override fun onError(e: Throwable) {
            e.printStackTrace()
            _airportsData.postValue(
                Resource(
                    Status.ERROR, null,
                    e.localizedMessage
                )
            )
        }

        override fun onComplete() {}

    }


}