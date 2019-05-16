package com.aliumujib.flightyy.presentation.models.schedule

import com.aliumujib.flightyy.presentation.models.AirlineModel

data class FlightModel(
    val arrivalModel: ArrivalDepartureModel,
    val departureModel: ArrivalDepartureModel,
    val airlineModel: AirlineModel,
    val flightNumber: Int,
    val stops: Int
)