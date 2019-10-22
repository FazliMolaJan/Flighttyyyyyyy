package com.aliumujib.flightyy.presentation.mappers

import com.aliumujib.flightyy.domain.models.schedule.Schedule
import com.aliumujib.flightyy.presentation.models.schedule.ScheduleModel
import javax.inject.Inject

class ScheduleMapper @Inject constructor(var flightMapper: FlightMapper) : Mapper<Schedule, ScheduleModel> {
    override fun mapToView(domain: Schedule): ScheduleModel {
        return ScheduleModel(domain.flights.map {
            flightMapper.mapToView(it)
        }, domain.duration)
    }

    override fun mapToDomain(view: ScheduleModel): Schedule {
        return Schedule(view.flightModels.map {
            flightMapper.mapToDomain(it)
        }, view.duration)
    }
}
