package com.aliumujib.flightyy.domain.test

import com.aliumujib.flightyy.domain.models.Airport
import com.aliumujib.flightyy.domain.models.Airline
import com.aliumujib.flightyy.domain.models.schedule.Flight
import com.aliumujib.flightyy.domain.models.schedule.Schedule
import konveyor.base.randomBuild

object DummyDataFactory {

    fun makeAirline(): Airline {
        return randomBuild()
    }

    fun makeFlight(): Flight {
        return randomBuild()
    }

    fun makeSchedule(): Schedule {
        return randomBuild()
    }

    fun makeAirport(): Airport {
        return randomBuild()
    }

    fun makeFlightSchedule(count: Int): List<Schedule> {
        val articles = mutableListOf<Schedule>()
        repeat(count) {
            articles.add(makeSchedule())
        }
        return articles
    }

    fun makeAirportList(count: Int): List<Airport> {
        val articles = mutableListOf<Airport>()
        repeat(count) {
            articles.add(makeAirport())
        }
        return articles
    }

}