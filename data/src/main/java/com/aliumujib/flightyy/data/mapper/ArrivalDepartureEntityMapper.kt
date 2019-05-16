package com.aliumujib.flightyy.data.mapper

import com.aliumujib.flightyy.data.model.schedule.ArrivalDepartureEntity
import com.aliumujib.flightyy.domain.models.schedule.ArrivalDeparture
import javax.inject.Inject

class ArrivalDepartureEntityMapper @Inject constructor(
    var airportEntityMapper: AirportEntityMapper) : EntityMapper<ArrivalDepartureEntity, ArrivalDeparture>() {

    override fun mapFromEntity(entity: ArrivalDepartureEntity): ArrivalDeparture {
        return ArrivalDeparture(
            airportEntityMapper.mapFromEntity(entity.airport),
            entity.scheduledTime
        )
    }

    override fun mapToEntity(domain: ArrivalDeparture): ArrivalDepartureEntity {
        return ArrivalDepartureEntity(
            airportEntityMapper.mapToEntity(domain.airport),
            domain.scheduledTime
        )
    }

}
