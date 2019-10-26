package com.aliumujib.flightyy.domain.usecases.airlines


import com.aliumujib.flightyy.domain.executor.PostExecutionThread
import com.aliumujib.flightyy.domain.models.Airline
import com.aliumujib.flightyy.domain.repositories.airlines.IAirlinesRepository
import com.aliumujib.flightyy.domain.usecases.base.SingleUseCase
import io.reactivex.Single
import javax.inject.Inject

class FetchAirlinesForID @Inject constructor(
    private val repository: IAirlinesRepository,
    postExecutionThread: PostExecutionThread
) : SingleUseCase<FetchAirlinesForID.Params, Airline>(postExecutionThread) {

    override fun buildUseCaseSingle(params: Params?): Single<Airline> {
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