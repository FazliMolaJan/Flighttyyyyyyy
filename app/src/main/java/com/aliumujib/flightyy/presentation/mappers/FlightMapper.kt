package com.aliumujib.flightyy.presentation.mappers

import com.aliumujib.flightyy.domain.models.schedule.Flight
import com.aliumujib.flightyy.presentation.models.schedule.FlightModel
import javax.inject.Inject

class FlightMapper @Inject constructor(
    var airlineMapper: AirlineMapper,
    var arrivalDepartureMapper: ArrivalDepartureMapper
) : Mapper<Flight, FlightModel> {

    override fun mapToView(domain: Flight): FlightModel {
        return FlightModel(
            arrivalDepartureMapper.mapToView(domain.arrival),
            arrivalDepartureMapper.mapToView(domain.departure),
            airlineMapper.mapToView(domain.airline),
            domain.flightNumber,
            domain.stops
        )
    }

    override fun mapToDomain(view: FlightModel): Flight {
        return Flight(
            arrivalDepartureMapper.mapToDomain(view.arrivalModel),
            arrivalDepartureMapper.mapToDomain(view.departureModel),
            airlineMapper.mapToDomain(view.airlineModel),
            view.flightNumber,
            view.stops
        )
    }


}
