package com.aliumujib.flightyy.data.mapper

import com.aliumujib.flightyy.domain.models.Airport
import com.aliumujib.flightyy.data.model.AirportEntity
import javax.inject.Inject

class AirportEntityMapper @Inject constructor() : EntityMapper<AirportEntity, Airport>() {

    override fun mapFromEntity(entity: AirportEntity): Airport {
        return Airport(
            entity.carriers,
            entity.city,
            entity.code,
            entity.country,
            entity.direct_flights,
            entity.elev?:"",
            entity.email,
            entity.icao,
            entity.lat,
            entity.lon,
            entity.name,
            entity.phone,
            entity.runway_length?:"",
            entity.state,
            entity.type,
            entity.tz,
            entity.url,
            entity.woeid
        )
    }

    override fun mapToEntity(domain: Airport): AirportEntity {
        return AirportEntity(
            domain.carriers,
            domain.city,
            domain.code,
            domain.country,
            domain.direct_flights,
            domain.elev,
            domain.email,
            domain.icao,
            domain.lat,
            domain.lon,
            domain.name,
            domain.phone,
            domain.runway_length,
            domain.state,
            domain.type,
            domain.tz,
            domain.url,
            domain.woeid
        )
    }

}
