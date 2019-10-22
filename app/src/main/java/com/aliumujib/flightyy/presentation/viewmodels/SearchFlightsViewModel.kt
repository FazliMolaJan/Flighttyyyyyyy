package com.aliumujib.flightyy.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.aliumujib.flightyy.domain.usecases.flights.FetchFlights
import com.aliumujib.flightyy.domain.models.schedule.Schedule
import com.aliumujib.flightyy.presentation.mappers.AirportMapper
import com.aliumujib.flightyy.presentation.mappers.ScheduleMapper
import com.aliumujib.flightyy.presentation.models.AirportModel
import com.aliumujib.flightyy.presentation.models.schedule.ScheduleModel
import com.aliumujib.flightyy.presentation.state.Resource
import com.aliumujib.flightyy.presentation.state.Status
import com.aliumujib.flightyy.ui.base.BaseViewModel
import io.reactivex.observers.DisposableObserver
import javax.inject.Inject

class SearchFlightsViewModel @Inject constructor(
    private val fetchFlights: FetchFlights,
    private val airportModelMapper: AirportMapper,
    private val scheduleModelMapper: ScheduleMapper
) : BaseViewModel() {

    private val _schedules: MutableLiveData<Resource<List<ScheduleModel>>> =
        MutableLiveData()
    val schedules: LiveData<Resource<List<ScheduleModel>>> = _schedules

    override fun onCleared() {
        fetchFlights.dispose()
        super.onCleared()
    }

    fun searchFlights(origin: AirportModel, destination: AirportModel, date: String) {
        _schedules.postValue(Resource(Status.LOADING, null, null))
        val mappedorigin = airportModelMapper.mapToDomain(origin)
        val mappeddest = airportModelMapper.mapToDomain(destination)

        return fetchFlights.execute(
            FetchScheduleDetailsSubscriber(),
            FetchFlights.Params(mappedorigin, mappeddest, date)
        )
    }

    inner class FetchScheduleDetailsSubscriber : DisposableObserver<List<Schedule>>() {
        override fun onNext(t: List<Schedule>) {
            _schedules.postValue(
                Resource(
                    Status.SUCCESS,
                    t.map {
                        scheduleModelMapper.mapToView(it)
                    }.sortedBy {
                        it.flightModels.size
                    }, null
                )
            )
        }

        override fun onError(e: Throwable) {
            e.printStackTrace()
            _schedules.postValue(
                Resource(
                    Status.ERROR, null,
                    e.localizedMessage
                )
            )
        }

        override fun onComplete() {}
    }
}