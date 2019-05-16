package com.aliumujib.flightyy.domain.interactors.airports


import com.aliumujib.flightyy.domain.executor.PostExecutionThread
import com.aliumujib.flightyy.domain.interactors.ObservableUseCase
import com.aliumujib.flightyy.domain.models.Airport
import com.aliumujib.flightyy.domain.repositories.airports.IAirportsRepository
import io.reactivex.Observable
import javax.inject.Inject

open class FetchAirports @Inject constructor(
    val repository: IAirportsRepository,
    postExecutionThread: PostExecutionThread
) : ObservableUseCase<Nothing, List<Airport>>(postExecutionThread) {


    override fun buildUseCaseObservable(params: Nothing?): Observable<List<Airport>> {
        return repository.searchAirports()
    }


}