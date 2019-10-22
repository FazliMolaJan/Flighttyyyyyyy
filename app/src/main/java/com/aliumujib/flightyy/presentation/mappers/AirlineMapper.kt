package com.aliumujib.flightyy.presentation.mappers

import com.aliumujib.flightyy.domain.models.Airline
import com.aliumujib.flightyy.presentation.models.AirlineModel
import javax.inject.Inject

class AirlineMapper @Inject constructor() : Mapper<Airline, AirlineModel> {
    override fun mapToView(domain: Airline): AirlineModel {
        return AirlineModel(
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

    override fun mapToDomain(view: AirlineModel): Airline {
        return Airline(
            view.active,
            view.alias,
            view.callsign,
            view.country,
            view.iata,
            view.icao,
            view.id,
            view.name
        )
    }
}
