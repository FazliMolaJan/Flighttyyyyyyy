package com.aliumujib.flightyy.domain.interactors.airlines


import com.aliumujib.flightyy.domain.executor.PostExecutionThread
import com.aliumujib.flightyy.domain.interactors.ObservableUseCase
import com.aliumujib.flightyy.domain.models.Airline
import com.aliumujib.flightyy.domain.repositories.airlines.IAirlinesRepository
import io.reactivex.Observable
import javax.inject.Inject

class FetchAirlinesForID @Inject constructor(
    val airlinesRepository: IAirlinesRepository,
    postExecutionThread: PostExecutionThread
) : ObservableUseCase<FetchAirlinesForID.Params, Airline>(postExecutionThread) {

    public override fun buildUseCaseObservable(params: Params?): Observable<Airline> {
        if (params == null) {
            throw IllegalStateException("Your params can't be null for this use case")
        }
        return airlinesRepository.getAirlineWithId(params.airlineId)
    }

    data class Params constructor(val airlineId: String) {
        companion object {
            fun make(airlineId: String): Params {
                return Params(airlineId)
            }
        }
    }
}