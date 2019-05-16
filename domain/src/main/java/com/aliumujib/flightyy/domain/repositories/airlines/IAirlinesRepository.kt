package com.aliumujib.flightyy.domain.repositories.airlines

import com.aliumujib.flightyy.domain.models.Airline
import io.reactivex.Observable


interface IAirlinesRepository {

    fun getAirlineWithId(id: String): Observable<Airline>

}