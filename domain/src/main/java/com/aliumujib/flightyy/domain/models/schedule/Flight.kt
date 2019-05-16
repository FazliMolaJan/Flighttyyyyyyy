package com.aliumujib.flightyy.domain.models.schedule

import com.aliumujib.flightyy.domain.models.Airline

data class Flight(
    val arrival: ArrivalDeparture,
    val departure: ArrivalDeparture,
    val airline: Airline,
    val flightNumber: Int,
    val stops: Int
)