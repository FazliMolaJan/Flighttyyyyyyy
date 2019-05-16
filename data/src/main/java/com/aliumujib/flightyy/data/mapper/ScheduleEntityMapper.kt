package com.aliumujib.flightyy.data.mapper

import com.aliumujib.flightyy.data.model.schedule.ScheduleEntity
import com.aliumujib.flightyy.domain.models.schedule.Schedule
import javax.inject.Inject

class ScheduleEntityMapper @Inject constructor(var flightEntityMapper: FlightEntityMapper): EntityMapper<ScheduleEntity, Schedule>() {

    override fun mapFromEntity(entity: ScheduleEntity): Schedule {
        return Schedule(flightEntityMapper.mapFromEntityList(entity.flightEntity), entity.duration)
    }

    override fun mapToEntity(domain: Schedule): ScheduleEntity {
        return ScheduleEntity(flightEntityMapper.mapFromDomainList(domain.flights), domain.duration)
    }

}
