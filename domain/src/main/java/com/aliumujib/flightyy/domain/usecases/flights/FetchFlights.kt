package com.aliumujib.flightyy.domain.usecases.flights


import com.aliumujib.flightyy.domain.executor.PostExecutionThread
import com.aliumujib.flightyy.domain.models.Airport
import com.aliumujib.flightyy.domain.models.schedule.Schedule
import com.aliumujib.flightyy.domain.repositories.schedules.ISchedulesRepository
import com.aliumujib.flightyy.domain.usecases.base.SingleUseCase
import io.reactivex.Single
import javax.inject.Inject

class FetchFlights @Inject constructor(
    private val repository: ISchedulesRepository,
    postExecutionThread: PostExecutionThread
) : SingleUseCase<FetchFlights.Params, List<Schedule>>(postExecutionThread) {

    public override fun buildUseCaseSingle(params: Params?): Single<List<Schedule>> {
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