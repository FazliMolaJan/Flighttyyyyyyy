package com.aliumujib.flightyy.domain.usecases.airports


import com.aliumujib.flightyy.domain.executor.PostExecutionThread
import com.aliumujib.flightyy.domain.models.Airport
import com.aliumujib.flightyy.domain.repositories.airports.IAirportsRepository
import com.aliumujib.flightyy.domain.usecases.base.SingleUseCase
import io.reactivex.Single
import javax.inject.Inject

class FetchAirports @Inject constructor(
    private val repository: IAirportsRepository,
    postExecutionThread: PostExecutionThread
) : SingleUseCase<Unit, List<Airport>>(postExecutionThread) {


    override fun buildUseCaseSingle(params: Unit?): Single<List<Airport>> {
        return repository.searchAirports()
    }


}