package com.aliumujib.flightyy.presentation.viewmodels


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.aliumujib.flightyy.domain.interactors.airports.FetchAirports
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

    private val _liveData: SingleLiveData<Resource<List<AirportModel>>> = SingleLiveData()
    val airportsData: LiveData<Resource<List<AirportModel>>> = _liveData

    private val airportList = mutableListOf<AirportModel>()

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
            _liveData.postValue(Resource(Status.ERROR, null, "Your query has to have at least 3 letters."))
            return
        }

        val results = airportList.filter { airport ->
            return@filter airport.city.contains(query, true) ||
                    airport.name.contains(query, true) ||
                    airport.state.contains(query, true)
        }

        _liveData.postValue(
            Resource(
                Status.SUCCESS,
                results, null
            )
        )
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
            _results.value = AirportSearchResults(selectedOriginAirport!!, selectedDestinationAirport!!)
        }
    }

    init {
        fetchAirports.execute(AirportListSubscriber())
    }

    inner class AirportListSubscriber : DisposableObserver<List<Airport>>() {
        override fun onNext(t: List<Airport>) {
            airportList.addAll(t.map { modelMapper.mapToView(it) })
        }

        override fun onError(e: Throwable) {
            e.printStackTrace()
            _liveData.postValue(
                Resource(
                    Status.ERROR, null,
                    e.localizedMessage
                )
            )
        }

        override fun onComplete() {}

    }


}