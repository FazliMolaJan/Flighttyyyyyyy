package com.aliumujib.flightyy.data.mapper

import com.aliumujib.flightyy.data.model.AirlineEntity
import com.aliumujib.flightyy.domain.models.Airline
import javax.inject.Inject

class AirlineEntityMapper @Inject constructor() : EntityMapper<AirlineEntity, Airline>() {

    override fun mapFromEntity(entity: AirlineEntity): Airline {
        return Airline(
            entity.active,
            entity.alias,
            entity.callsign,
            entity.country,
            entity.iata,
            entity.icao,
            entity.id,
            entity.name
        )
    }

    override fun mapToEntity(domain: Airline): AirlineEntity {
        return AirlineEntity(
            domain.active,
            domain.alias,
            domain.callsign,
            domain.country,
            domain.iata,
            domain.icao,
            domain.id,
            domain.name
        )
    }


}
