package com.aliumujib.flightyy.data

import com.aliumujib.flightyy.data.model.AirlineEntity
import com.aliumujib.flightyy.data.model.AirportEntity
import com.aliumujib.flightyy.data.model.schedule.ArrivalDepartureEntity
import com.aliumujib.flightyy.data.model.schedule.FlightEntity
import com.aliumujib.flightyy.data.model.schedule.ScheduleEntity
import com.aliumujib.flightyy.domain.models.Airline
import com.aliumujib.flightyy.domain.models.Airport
import com.aliumujib.flightyy.domain.models.schedule.Schedule
import konveyor.base.randomBuild
import java.util.*

object DummyDataFactory {

    fun makeRandomFlightEntity(): FlightEntity {
        return randomBuild()
    }

    fun makeRandomArrivalDepartureEntity(): ArrivalDepartureEntity {
        return randomBuild()
    }

    fun makeRandomAirlineEntity(): AirlineEntity {
        return randomBuild()
    }

    fun makeRandomAirline(): Airline {
        return randomBuild()
    }

    fun makeRandomAirportEntity(): AirportEntity {
        return AirportEntity(
            randomBuild(),
            randomBuild(),
            randomBuild(),
            randomBuild(),
            randomBuild(),
            randomBuild(),
            randomBuild(),
            randomBuild(),
            Random().nextDouble().toString(),
            Random().nextDouble().toString(),
            randomBuild(),
            randomBuild(),
            randomBuild(),
            randomBuild(),
            randomBuild(),
            randomBuild(),
            randomBuild(),
            randomBuild()
        )
    }


    fun makeRandomAirport(): Airport {
        return randomBuild()
    }

    fun makeScheduleEntity(): ScheduleEntity {
        return randomBuild()
    }

    fun makeSchedule(): Schedule {
        return randomBuild()
    }

    fun makeAirportEntities(count: Int): List<AirportEntity> {
        val articles = mutableListOf<AirportEntity>()
        repeat(count) {
            articles.add(makeRandomAirportEntity())
        }
        return articles
    }

    fun makeScheduleEntities(count: Int): List<ScheduleEntity> {
        val schedules = mutableListOf<ScheduleEntity>()
        repeat(count) {
            schedules.add(makeScheduleEntity())
        }
        return schedules
    }

    fun makeSchedules(count: Int): List<Schedule> {
        val schedules = mutableListOf<Schedule>()
        repeat(count) {
            schedules.add(makeSchedule())
        }
        return schedules
    }

    fun makeAirlineEntities(count: Int): List<AirlineEntity> {
        val articles = mutableListOf<AirlineEntity>()
        repeat(count) {
            articles.add(makeRandomAirlineEntity())
        }
        return articles
    }

}