package com.aliumujib.flightyy.domain.repositories.airlines

import com.aliumujib.flightyy.domain.models.Airline
import io.reactivex.Single


interface IAirlinesRepository {

    fun getAirlineWithId(id: String): Single<Airline>

}