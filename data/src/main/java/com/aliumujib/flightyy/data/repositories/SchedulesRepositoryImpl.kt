package com.aliumujib.flightyy.data.repositories

import com.aliumujib.flightyy.data.contracts.remote.ISchedulesRemote
import com.aliumujib.flightyy.data.mapper.ScheduleEntityMapper
import com.aliumujib.flightyy.domain.models.Airport
import com.aliumujib.flightyy.domain.models.schedule.Schedule
import com.aliumujib.flightyy.domain.repositories.schedules.ISchedulesRepository
import io.reactivex.Observable
import javax.inject.Inject

class SchedulesRepositoryImpl @Inject constructor(
    val remote: ISchedulesRemote,
    private val entityMapper: ScheduleEntityMapper
) : ISchedulesRepository {

    override fun getFlightSchedules(origin: Airport, destination: Airport, date: String): Observable<List<Schedule>> {
        return remote.getSchedules(origin.code, destination.code, date).map {
            entityMapper.mapFromEntityList(it)
        }
    }

}