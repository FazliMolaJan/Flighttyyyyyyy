package com.aliumujib.flightyy.domain.repositories.airports

import com.aliumujib.flightyy.domain.models.Airport
import io.reactivex.Single


interface IAirportsRepository {

    fun searchAirports(): Single<List<Airport>>

}