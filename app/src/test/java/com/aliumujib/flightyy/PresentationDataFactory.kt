package com.aliumujib.flightyy

import com.aliumujib.flightyy.domain.models.Airport
import com.aliumujib.flightyy.domain.models.schedule.Flight
import com.aliumujib.flightyy.domain.models.schedule.Schedule
import com.aliumujib.flightyy.presentation.models.AirportModel
import com.aliumujib.flightyy.presentation.models.schedule.FlightModel
import com.aliumujib.flightyy.presentation.models.schedule.ScheduleModel
import konveyor.base.randomBuild

object PresentationDataFactory {

    fun makeAirportModel(): AirportModel {
        return randomBuild()
    }


    fun makeScheduleList(count: Int): List<Schedule> {
        val list = mutableListOf<Schedule>()
        repeat(count) {
            list.add(randomBuild())
        }
        return list
    }


    fun makeAirportList(count: Int): List<Airport> {
        val mutableList = mutableListOf<Airport>()
        repeat(count) {
            mutableList.add(randomBuild())
        }
        return mutableList
    }
}