package com.aliumujib.flightyy.domain.repositories.schedules

import com.aliumujib.flightyy.domain.models.Airport
import com.aliumujib.flightyy.domain.models.schedule.Schedule
import io.reactivex.Single


interface ISchedulesRepository {

    fun getFlightSchedules(origin: Airport, destination: Airport, date: String): Single<List<Schedule>>

}