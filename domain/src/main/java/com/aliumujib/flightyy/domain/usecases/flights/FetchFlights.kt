package com.aliumujib.flightyy.domain.usecases.flights


import com.aliumujib.flightyy.domain.executor.PostExecutionThread
import com.aliumujib.flightyy.domain.usecases.base.ObservableUseCase
import com.aliumujib.flightyy.domain.models.Airport
import com.aliumujib.flightyy.domain.models.schedule.Schedule
import com.aliumujib.flightyy.domain.repositories.schedules.ISchedulesRepository
import io.reactivex.Observable
import javax.inject.Inject

class FetchFlights @Inject constructor(
    private val repository: ISchedulesRepository,
    postExecutionThread: PostExecutionThread
) : ObservableUseCase<FetchFlights.Params, List<Schedule>>(postExecutionThread) {

    public override fun buildUseCaseObservable(params: Params?): Observable<List<Schedule>> {
        checkNotNull(params) { "Your params can't be null for this use case" }

        return repository.getFlightSchedules(params.origin, params.destination, params.date)
    }

    data class Params constructor(val origin: Airport, val destination: Airport, val date: String) {
        companion object {
            fun make(origin: Airport, destination: Airport, date: String): Params {
                return Params(origin, destination, date)
            }
        }
    }

}