package com.aliumujib.flightyy.presentation.mappers

import com.aliumujib.flightyy.domain.models.Airport
import com.aliumujib.flightyy.presentation.models.AirportModel
import javax.inject.Inject

class AirportMapper @Inject constructor() : Mapper<Airport, AirportModel> {

    override fun mapToView(domain: Airport): AirportModel {
        return AirportModel(
            domain.carriers,
            domain.city,
            domain.code,
            domain.country,
            domain.direct_flights,
            domain.elev ?: "",
            domain.email ?: "",
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

    override fun mapToDomain(view: AirportModel): Airport {
        return Airport(
            view.carriers,
            view.city,
            view.code,
            view.country,
            view.direct_flights,
            view.elev,
            view.email,
            view.icao,
            view.lat,
            view.lon,
            view.name,
            view.phone,
            view.runway_length,
            view.state,
            view.type,
            view.tz,
            view.url,
            view.woeid
        )
    }


}
