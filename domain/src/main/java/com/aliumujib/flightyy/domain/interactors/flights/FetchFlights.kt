package com.aliumujib.flightyy.domain.interactors.flights


import com.aliumujib.flightyy.domain.executor.PostExecutionThread
import com.aliumujib.flightyy.domain.interactors.ObservableUseCase
import com.aliumujib.flightyy.domain.models.Airport
import com.aliumujib.flightyy.domain.models.schedule.Schedule
import com.aliumujib.flightyy.domain.repositories.schedules.ISchedulesRepository
import io.reactivex.Observable
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class FetchFlights @Inject constructor(
    val schedulesRepository: ISchedulesRepository,
    postExecutionThread: PostExecutionThread
) : ObservableUseCase<FetchFlights.Params, List<Schedule>>(postExecutionThread) {

    public override fun buildUseCaseObservable(params: Params?): Observable<List<Schedule>> {
        if (params == null) {
            throw IllegalStateException("Your params can't be null for this use case")
        }

        val dateFormatter = SimpleDateFormat("yyyy-mm-dd")
        return schedulesRepository.getFlightSchedules(params.origin, params.destination, dateFormatter.format(params.date))
    }

    data class Params constructor(val origin: Airport, val destination: Airport, val date: Date) {
        companion object {
            fun make(origin: Airport, destination: Airport, date: Date): Params {
                return Params(origin, destination, date)
            }
        }
    }

}