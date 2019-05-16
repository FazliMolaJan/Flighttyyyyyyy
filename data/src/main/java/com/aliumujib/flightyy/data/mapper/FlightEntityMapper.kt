package com.aliumujib.flightyy.data.mapper

import com.aliumujib.flightyy.data.model.schedule.FlightEntity
import com.aliumujib.flightyy.domain.models.schedule.Flight
import javax.inject.Inject

class FlightEntityMapper @Inject constructor(
    var airlineEntityMapper: AirlineEntityMapper,
    var arrivalDepartureEntityMapper: ArrivalDepartureEntityMapper
) : EntityMapper<FlightEntity, Flight>() {

    override fun mapFromEntity(entity: FlightEntity): Flight {
        return Flight(
            arrivalDepartureEntityMapper.mapFromEntity(entity.arrival),
            arrivalDepartureEntityMapper.mapFromEntity(entity.departure),
            airlineEntityMapper.mapFromEntity(entity.airline),
            entity.flightNumber,
            entity.stops
        )
    }

    override fun mapToEntity(domain: Flight): FlightEntity {
        return FlightEntity(
            arrivalDepartureEntityMapper.mapToEntity(domain.arrival),
            arrivalDepartureEntityMapper.mapToEntity(domain.departure),
            airlineEntityMapper.mapToEntity(domain.airline),
            domain.flightNumber,
            domain.stops
        )
    }

}
