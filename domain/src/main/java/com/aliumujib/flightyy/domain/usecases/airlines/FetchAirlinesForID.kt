package com.aliumujib.flightyy.domain.usecases.airlines


import com.aliumujib.flightyy.domain.executor.PostExecutionThread
import com.aliumujib.flightyy.domain.usecases.base.ObservableUseCase
import com.aliumujib.flightyy.domain.models.Airline
import com.aliumujib.flightyy.domain.repositories.airlines.IAirlinesRepository
import io.reactivex.Observable
import javax.inject.Inject

class FetchAirlinesForID @Inject constructor(
    private val repository: IAirlinesRepository,
    postExecutionThread: PostExecutionThread
) : ObservableUseCase<FetchAirlinesForID.Params, Airline>(postExecutionThread) {

    override fun buildUseCaseObservable(params: Params?): Observable<Airline> {
        checkNotNull(params) { "Your params can't be null for this use case" }
        return repository.getAirlineWithId(params.airlineId)
    }

    data class Params constructor(val airlineId: String) {
        companion object {
            fun make(airlineId: String): Params {
                return Params(airlineId)
            }
        }
    }
}