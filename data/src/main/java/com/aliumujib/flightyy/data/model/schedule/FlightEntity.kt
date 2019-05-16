package com.aliumujib.flightyy.data.model.schedule

import com.aliumujib.flightyy.data.model.AirlineEntity
import com.aliumujib.flightyy.domain.models.Airline
import com.aliumujib.flightyy.domain.models.schedule.ArrivalDeparture

data class FlightEntity(
    val arrival: ArrivalDepartureEntity,
    val departure: ArrivalDepartureEntity,
    val airline: AirlineEntity,
    val flightNumber: Int,
    val stops: Int
)