package com.aliumujib.flightyy.presentation.mappers

import com.aliumujib.flightyy.domain.models.schedule.ArrivalDeparture
import com.aliumujib.flightyy.presentation.models.schedule.ArrivalDepartureModel
import javax.inject.Inject

class ArrivalDepartureMapper @Inject constructor(
    var airportMapper: AirportMapper
) : Mapper<ArrivalDeparture, ArrivalDepartureModel> {


    override fun mapToDomain(view: ArrivalDepartureModel): ArrivalDeparture {
        return ArrivalDeparture(
            airportMapper.mapToDomain(view.airportModel),
            view.scheduledTime
        )
    }

    override fun mapToView(domain: ArrivalDeparture): ArrivalDepartureModel {
        return ArrivalDepartureModel(
            airportMapper.mapToView(domain.airport),
            domain.scheduledTime
        )
    }



}
