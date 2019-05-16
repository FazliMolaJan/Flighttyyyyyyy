package com.aliumujib.flightyy.domain.repositories.airports

import com.aliumujib.flightyy.domain.models.Airport
import io.reactivex.Observable


interface IAirportsRepository {

    fun searchAirports(): Observable<List<Airport>>

}